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
package org.asteriskjava.manager.impl;

import junit.framework.TestCase;

import org.asteriskjava.io.SocketConnectionFacade;
import org.asteriskjava.manager.ManagerWriter;
import org.asteriskjava.manager.action.StatusAction;
import org.asteriskjava.manager.impl.ManagerWriterImpl;
import org.easymock.MockControl;

public class ManagerWriterImplTest extends TestCase
{
    private ManagerWriter managerWriter;
    
    public void setUp()
    {
        managerWriter = new ManagerWriterImpl();
    }
    
    public void testSendActionWithoutSocket() throws Exception
    {
        try
        {
            managerWriter.sendAction(new StatusAction(), null);
            fail("Must throw IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            assertTrue("Exception must be of type IllegalStateException", e instanceof IllegalStateException);
        }
    }

    public void testSendAction() throws Exception
    {
        MockControl socketMC;
        SocketConnectionFacade socketConnectionFacade;

        socketMC = MockControl.createControl(SocketConnectionFacade.class);
        socketConnectionFacade = (SocketConnectionFacade) socketMC.getMock();
        socketConnectionFacade.write("action: Status\r\n\r\n");
        socketConnectionFacade.flush();
        socketMC.replay();

        managerWriter.setSocket(socketConnectionFacade);
        managerWriter.sendAction(new StatusAction(), null);
        socketMC.verify();
    }
}
