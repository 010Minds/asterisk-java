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
package org.asteriskjava.fastagi;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.asteriskjava.io.ServerSocketFacade;
import org.asteriskjava.io.SocketConnectionFacade;
import org.asteriskjava.io.internal.ServerSocketFacadeImpl;
import org.asteriskjava.util.Log;
import org.asteriskjava.util.LogFactory;



public class DefaultAgiServer implements AgiServer
{
    /**
     * The default name of the resource bundle that contains the config.
     */
    private static final String DEFAULT_CONFIG_RESOURCE_BUNDLE_NAME = "fastagi";

    /**
     * The default bind port.
     */
    private static final int DEFAULT_BIND_PORT = 4573;

    /**
     * The default thread pool size.
     */
    private static final int DEFAULT_POOL_SIZE = 10;

    /**
     * Instance logger.
     */
    private final Log logger = LogFactory.getLog(DefaultAgiServer.class);

    private ServerSocketFacade serverSocket;

    /**
     * The port to listen on.
     */
    private int port;

    /**
     * The thread pool that contains the worker threads to process incoming
     * requests.
     */
    private ThreadPoolExecutor pool;

    /**
     * The minimum number of worker threads in the thread pool.
     */
    private int poolSize;

    /**
     * The maximum number of worker threads in the thread pool. This equals the maximum
     * number of concurrent requests this AgiServer can serve.
     */
    private int maximumPoolSize;
    
    /**
     * True while this server is shut down.
     */
    private boolean die;

    /**
     * The strategy to use for mapping AgiRequests to AgiScripts that serve
     * them.
     */
    private MappingStrategy mappingStrategy;

    /**
     * Creates a new DefaultAgiServer.
     * 
     */
    public DefaultAgiServer()
    {
        this.port = DEFAULT_BIND_PORT;
        this.poolSize = DEFAULT_POOL_SIZE;
        this.maximumPoolSize = this.poolSize;
        this.mappingStrategy = new CompositeMappingStrategy(
                new ResourceBundleMappingStrategy(),
                new ClassNameMappingStrategy());
        
        loadConfig();
    }

    /**
     * Sets the number of worker threads in the thread pool.<br>
     * This is the number of threads that are available even if they are idle.<br>
     * The default pool size is 10.
     * 
     * @param poolSize the size of the worker thread pool.
     */
    public void setPoolSize(int poolSize)
    {
        this.poolSize = poolSize;
    }

    /**
     * Sets the maximum number of worker threads in the thread pool.<br>
     * This equals the maximum number of concurrent requests this AgiServer can
     * serve.<br>
     * The default maximum pool size is 10.
     * 
     * @param maximumPoolSize the maximum size of the worker thread pool.
     */
    public void setMaximumPoolSize(int maximumPoolSize)
    {
        this.maximumPoolSize = maximumPoolSize;
    }
    
    /**
     * Sets the TCP port to listen on for new connections.<br>
     * The default port is 4573.
     * 
     * @param bindPort the port to bind to.
     * @deprecated use {@see #setPort(int)} instead
     */
    public void setBindPort(int bindPort)
    {
        this.port = bindPort;
    }
    
    /**
     * Sets the TCP port to listen on for new connections.<br>
     * The default port is 4573.
     * 
     * @param port the port to bind to.
     * @since 0.2
     */
    public void setPort(int port)
    {
        this.port = port;
    }

    /**
     * Sets the strategy to use for mapping AgiRequests to AgiScripts that serve
     * them.<br>
     * The default mapping strategy is a ResourceBundleMappingStrategy.
     * 
     * @param mappingStrategy the mapping strategy to use.
     * @see ResourceBundleMappingStrategy
     */
    public void setMappingStrategy(MappingStrategy mappingStrategy)
    {
        this.mappingStrategy = mappingStrategy;
    }

    private void loadConfig()
    {
        ResourceBundle resourceBundle;

        try
        {
            resourceBundle = ResourceBundle
                    .getBundle(DEFAULT_CONFIG_RESOURCE_BUNDLE_NAME);
        }
        catch (MissingResourceException e)
        {
            return;
        }
        
        try
        {
            String portString;

            portString = resourceBundle.getString("port");
            if (portString == null)
            {
                // for backward compatibility only
                portString = resourceBundle.getString("bindPort");
            }
            port = Integer.parseInt(portString);
        }
        catch(Exception e)
        {
            //swallow
        }

        try
        {
            String poolSizeString;

            poolSizeString = resourceBundle.getString("poolSize");
            poolSize = Integer.parseInt(poolSizeString);
        }
        catch(Exception e)
        {
            //swallow
        }
    }

    protected ServerSocketFacade createServerSocket() throws IOException
    {
        return new ServerSocketFacadeImpl(port, 0, null);
    }

    public void startup() throws IOException, IllegalStateException
    {
        SocketConnectionFacade socket;
        AgiConnectionHandler connectionHandler;

        die = false;
        pool = new ThreadPoolExecutor(
                poolSize, (maximumPoolSize < poolSize) ? poolSize : maximumPoolSize, 
                50000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        logger.info("Thread pool started.");

        try
        {
            serverSocket = createServerSocket();
        }
        catch (IOException e)
        {
            logger.error("Unable start AgiServer: cannot to bind to *:" + port + ".", e);
            throw e;
        }
        
        logger.info("Listening on *:" + port + ".");

        try
        {
            while ((socket = serverSocket.accept()) != null)
            {
                logger.info("Received connection.");
                connectionHandler = new AgiConnectionHandler(socket,
                        mappingStrategy);
                pool.execute(connectionHandler);
            }
        }
        catch (IOException e)
        {
            // swallow only if shutdown
            if (!die)
            {
                logger.error("IOException while waiting for connections.", e);
                throw e;
            }
        }
        finally
        {
            if (serverSocket != null)
            {
                try
                {
                    serverSocket.close();
                }
                catch (IOException e)
                {
                    // swallow
                }
            }
            serverSocket = null;
            pool.shutdown();
            logger.info("AgiServer shut down.");
        }
    }

    public void run()
    {
        try
        {
            startup();
        }
        catch (IOException e)
        {
            // nothing we can do about that...
        }
    }

    public void die() throws IOException
    {
        die = true;

        if (serverSocket != null)
        {
            serverSocket.close();
        }
    }

    public void shutdown() throws IOException, IllegalStateException
    {
        die();
    }

    public static void main(String[] args) throws Exception
    {
        AgiServer server;

        server = new DefaultAgiServer();
        server.startup();
    }
}
