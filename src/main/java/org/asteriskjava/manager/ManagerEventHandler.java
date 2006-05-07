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
package org.asteriskjava.manager;

import java.util.EventListener;

import org.asteriskjava.manager.event.ManagerEvent;

/**
 * This interface is deprecated, please use {@link org.asteriskjava.manager.ManagerEventListener}
 * instead.
 * 
 * @see org.asteriskjava.manager.event.ManagerEvent
 * @author srt
 * @version $Id$
 * @deprecated this interface is deprecated and will be removed in Asterisk-Java 0.4. 
 */
public interface ManagerEventHandler extends EventListener
{
    /**
     * This method is called when an event is received.
     * 
     * @param event the event received
     */
    void handleEvent(ManagerEvent event);
}
