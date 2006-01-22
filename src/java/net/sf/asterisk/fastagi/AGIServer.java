/*
 * Copyright  2004-2005 Stefan Reuter
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
package net.sf.asterisk.fastagi;

import java.io.IOException;

/**
 * Listens for incoming AGI connections, reads the inital data and builds an
 * AGIRequest using an AGIRequestBuilder.<br>
 * The AGIRequest is then handed over to the appropriate AGIScript for
 * processing.
 * 
 * @author srt
 * @version $Id: AGIServer.java,v 1.3 2005/03/08 16:48:34 srt Exp $
 */
public interface AGIServer
{
    /**
     * Starts this AGIServer.<br>
     * After calling startup() this AGIServer is ready to receive requests from
     * Asterisk servers and process them.
     * 
     * @throws IOException if the server socket cannot be bound.
     * @throws IllegalStateException if this AGIServer is already running.
     */
    void startup() throws IOException, IllegalStateException;

    /**
     * Shuts this AGIServer down.<br>
     * The server socket is closed and all resources are freed.
     * 
     * @throws IOException if the connection cannot be shut down.
     * @throws IllegalStateException if this AGIServer is already shut down or
     *             has not yet been started.
     */
    void shutdown() throws IOException, IllegalStateException;
}
