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
package org.asteriskjava.fastagi.impl;

import java.io.IOException;

import org.asteriskjava.fastagi.AGIException;
import org.asteriskjava.fastagi.AGINetworkException;
import org.asteriskjava.fastagi.AGIWriter;
import org.asteriskjava.fastagi.command.AGICommand;
import org.asteriskjava.io.SocketConnectionFacade;


/**
 * Default implementation of the AGIWriter interface.
 * 
 * @author srt
 * @version $Id: AGIWriterImpl.java,v 1.1 2005/03/11 15:20:50 srt Exp $
 */
public class AGIWriterImpl implements AGIWriter
{
    private SocketConnectionFacade socket;

    public AGIWriterImpl(SocketConnectionFacade socket)
    {
        this.socket = socket;
    }

    public void sendCommand(AGICommand command) throws AGIException
    {
        try
        {
            socket.write(command.buildCommand() + "\n");
            socket.flush();
        }
        catch (IOException e)
        {
            throw new AGINetworkException(
                    "Unable to send command to Asterisk: " + e.getMessage(), e);
        }
    }
}
