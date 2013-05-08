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
package org.asteriskjava.manager.event;

/**
 * An AntennaLevel is triggered when GSM channels change signal antenna level.
 * <p>
 * It is implemented in <code>channels/chan_khomp.c</code> 
 * 
 * @author srt
 * @version $Id$
 */
public class AntennaLevelEvent extends ManagerEvent {
    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = 7125917930904957919L;
    private String signal;
    private String channel;

    /**
     * @param source
     */
    public AntennaLevelEvent(Object source) {
	super(source);
    }

    /**
     * Returns the name of the signal of that channel.
     */
    public String getSignal() {
	return signal;
    }

    /**
     * Sets the name of the signal that channel.
     */
    public void setSignal(String signal) {
	this.signal = signal;
    }

    /**
     * Returns the name of the channel associated with the logged in agent.
     * 
     * @return the name of the channel associated with the logged in agent.
     * @since 0.3
     */
    public String getChannel() {
	return channel;
    }

    /**
     * Sets the name of the channel associated with the logged in agent.
     * 
     * @param channel
     *            the name of the channel associated with the logged in agent.
     * @since 0.3
     */
    public void setChannel(String channel) {
	this.channel = channel;
    }

}
