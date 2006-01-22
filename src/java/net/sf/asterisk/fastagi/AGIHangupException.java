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

/**
 * The AGIHangupException is thrown if the channel has been hang up while
 * processing the AGIRequest.
 * 
 * @author srt
 * @version $Id: AGIHangupException.java,v 1.2 2005/07/30 19:57:16 srt Exp $
 */
public class AGIHangupException extends AGIException
{
    /**
     * Serial version identifier.
     */
    private static final long serialVersionUID = 3256444698691252274L;
    
    public AGIHangupException()
    {
        super("Channel was hung up.");
    }
}
