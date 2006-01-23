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
package org.asteriskjava.fastagi;

import org.asteriskjava.fastagi.reply.AGIReply;

/**
 * The AGIReader reads the replies from the network and parses them using a
 * ReplyBuilder.
 * 
 * @author srt
 * @version $Id: AGIReader.java,v 1.5 2005/03/11 09:37:37 srt Exp $
 */
public interface AGIReader
{
    /**
     * Reads the initial request data from Asterisk.
     * 
     * @return the request read.
     * @throws AGIException if the request can't be read.
     */
    AGIRequest readRequest() throws AGIException;

    /**
     * Reads one reply to an AGICommand from Asterisk.
     * 
     * @return the reply read.
     * @throws AGIException if the reply can't be read.
     */
    AGIReply readReply() throws AGIException;
}
