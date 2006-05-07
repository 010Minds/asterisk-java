/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava.fastagi.internal;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiReader;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.AgiScript;
import org.asteriskjava.fastagi.AgiWriter;
import org.asteriskjava.fastagi.MappingStrategy;
import org.asteriskjava.fastagi.command.VerboseCommand;
import org.asteriskjava.io.SocketConnectionFacade;
import org.asteriskjava.util.Log;
import org.asteriskjava.util.LogFactory;

/**
 * An AgiConnectionHandler is created and run by the AgiServer whenever a new
 * socket connection from an Asterisk Server is received.<br>
 * It reads the request using an AgiReader and runs the AgiScript configured to
 * handle this type of request. Finally it closes the socket connection.
 * 
 * @author srt
 * @version $Id$
 */
public class AgiConnectionHandler implements Runnable
{
    private final Log logger = LogFactory.getLog(getClass());
    private static final ThreadLocal<AgiChannel> channel = new ThreadLocal<AgiChannel>();

    /**
     * The socket connection.
     */
    private SocketConnectionFacade socket;

    /**
     * The strategy to use to determine which script to run.
     */
    private MappingStrategy mappingStrategy;

    /**
     * Creates a new AGIConnectionHandler to handle the given socket connection.
     * 
     * @param socket the socket connection to handle.
     * @param mappingStrategy the strategy to use to determine which script to
     *            run.
     */
    public AgiConnectionHandler(SocketConnectionFacade socket,
            MappingStrategy mappingStrategy)
    {
        this.socket = socket;
        this.mappingStrategy = mappingStrategy;
    }

    protected AgiReader createReader()
    {
        return new AgiReaderImpl(socket);
    }

    protected AgiWriter createWriter()
    {
        return new AgiWriterImpl(socket);
    }

    public void run()
    {
        try
        {
            AgiReader reader;
            AgiWriter writer;
            AgiRequest request;
            AgiChannel channel;
            AgiScript script;
            Thread thread;
            String threadName;

            reader = createReader();
            writer = createWriter();

            request = reader.readRequest();
            channel = new AgiChannelImpl(writer, reader);

            script = mappingStrategy.determineScript(request);

            thread = Thread.currentThread();
            threadName = thread.getName();
            AgiConnectionHandler.channel.set(channel);

            if (script != null)
            {
                logger.info("Begin AGIScript " + script.getClass().getName()
                        + " on " + threadName);
                script.service(request, channel);
                logger.info("End AGIScript " + script.getClass().getName()
                        + " on " + threadName);
            }
            else
            {
                String error;

                error = "No script configured for URL '"
                        + request.getRequestURL() + "' (script '"
                        + request.getScript() + "')";
                logger.error(error);

                try
                {
                    channel.sendCommand(new VerboseCommand(error, 1));
                }
                catch (AgiException e)
                {
                    // do nothing
                }
            }
        }
        catch (AgiException e)
        {
            logger.error("AgiException while handling request", e);
        }
        catch (Exception e)
        {
            logger.error("Unexpected Exception while handling request", e);
        }
        finally
        {
            AgiConnectionHandler.channel.set(null);
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                // swallow
            }
        }
    }

    /**
     * Returns the AgiChannel associated with the current thread.
     * 
     * @return the AgiChannel associated with the current thread or
     *         <code>null</code> if none is associated.
     */
    public static AgiChannel getChannel()
    {
        return AgiConnectionHandler.channel.get();
    }
}
