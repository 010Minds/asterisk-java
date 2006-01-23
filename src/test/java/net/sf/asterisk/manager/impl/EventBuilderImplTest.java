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
package net.sf.asterisk.manager.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.asterisk.manager.EventBuilder;
import net.sf.asterisk.manager.event.CdrEvent;
import net.sf.asterisk.manager.event.ChannelEvent;
import net.sf.asterisk.manager.event.HangupEvent;
import net.sf.asterisk.manager.event.LogChannelEvent;
import net.sf.asterisk.manager.event.ManagerEvent;
import net.sf.asterisk.manager.event.NewCallerIdEvent;
import net.sf.asterisk.manager.event.NewChannelEvent;
import net.sf.asterisk.manager.event.NewExtenEvent;
import net.sf.asterisk.manager.event.ResponseEvent;
import net.sf.asterisk.manager.event.ShutdownEvent;
import net.sf.asterisk.manager.event.StatusCompleteEvent;

/**
 * @author srt
 * @version $Id: EventBuilderImplTest.java,v 1.7 2005/08/28 12:28:41 srt Exp $
 */
public class EventBuilderImplTest extends TestCase
{
    private EventBuilder eventBuilder;

    public void setUp()
    {
        this.eventBuilder = new EventBuilderImpl();
    }

    public void testRegisterEvent()
    {
        eventBuilder.registerEventClass(NewChannelEvent.class);
    }

    public void testRegisterUserEventWithA()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        eventBuilder.registerEventClass(A.class);
        properties.put("event", "UserEventA");
        event = eventBuilder.buildEvent(this, properties);

        assertTrue("Wrong type", event instanceof A);
    }

    public void testRegisterUserEventWithBEvent()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        eventBuilder.registerEventClass(BEvent.class);
        properties.put("event", "UserEventB");
        event = eventBuilder.buildEvent(this, properties);

        assertTrue("Wrong type", event instanceof BEvent);
    }

    public void testRegisterUserEventWithUserEventC()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        eventBuilder.registerEventClass(UserEventC.class);
        properties.put("event", "UserEventC");
        event = eventBuilder.buildEvent(this, properties);

        assertTrue("Wrong type", event instanceof UserEventC);
    }

    public void testRegisterUserEventWithUserEventDEvent()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        eventBuilder.registerEventClass(UserEventDEvent.class);
        properties.put("event", "UserEventD");
        event = eventBuilder.buildEvent(this, properties);

        assertTrue("Wrong type", event instanceof UserEventDEvent);
    }

    public void testRegisterEventWithAbstractEvent()
    {
        try
        {
            eventBuilder.registerEventClass(ChannelEvent.class);
            fail("registerEvent() must not accept abstract classes");
        }
        catch (IllegalArgumentException ex)
        {
        }
    }

    public void testRegisterEventWithWrongClass()
    {
        try
        {
            eventBuilder.registerEventClass(String.class);
            fail("registerEvent() must only accept subclasses of ManagerEvent");
        }
        catch (IllegalArgumentException ex)
        {
        }
    }

    /*
     * public void testGetSetters() { Map setters; EventBuilderImpl eventBuilder =
     * getEventBuilder(); setters =
     * eventBuilder.getSetters(NewChannelEvent.class); assertTrue("Setter not
     * found", setters.containsKey("callerid")); }
     */

    public void testBuildEventWithMixedCaseSetter()
    {
        Map properties = new HashMap();
        String callerid = "1234";
        NewChannelEvent event;

        properties.put("event", "Newchannel");
        properties.put("callerid", callerid);
        event = (NewChannelEvent) eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Returned event is of wrong type", NewChannelEvent.class,
                event.getClass());
        assertEquals("String property not set correctly", callerid, event
                .getCallerId());
        assertEquals("Source not set correctly", this, event.getSource());
    }

    public void testBuildEventWithIntegerProperty()
    {
        Map properties = new HashMap();
        String channel = "SIP/1234";
        Integer priority = new Integer(1);
        NewExtenEvent event;

        properties.put("event", "newexten");
        properties.put("channel", channel);
        properties.put("priority", priority.toString());
        event = (NewExtenEvent) eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Returned event is of wrong type", NewExtenEvent.class,
                event.getClass());
        assertEquals("String property not set correctly", channel, event
                .getChannel());
        assertEquals("Integer property not set correctly", priority, event
                .getPriority());
    }

    public void testBuildEventWithBooleanProperty()
    {
        Map properties = new HashMap();
        ShutdownEvent event;

        eventBuilder.registerEventClass(ShutdownEvent.class);
        properties.put("event", "shutdown");
        properties.put("restart", "True");
        event = (ShutdownEvent) eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Returned event is of wrong type", ShutdownEvent.class,
                event.getClass());
        assertEquals("Boolean property not set correctly", Boolean.TRUE, event
                .getRestart());
    }

    public void testBuildEventWithBooleanPropertyOfValueYes()
    {
        Map properties = new HashMap();
        ShutdownEvent event;

        eventBuilder.registerEventClass(ShutdownEvent.class);
        properties.put("event", "shutdown");
        properties.put("restart", "yes");
        event = (ShutdownEvent) eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Returned event is of wrong type", ShutdownEvent.class,
                event.getClass());
        assertEquals("Boolean property not set correctly", Boolean.TRUE, event
                .getRestart());
    }

    public void testBuildEventWithBooleanPropertyOfValueNo()
    {
        Map properties = new HashMap();
        ShutdownEvent event;

        eventBuilder.registerEventClass(ShutdownEvent.class);
        properties.put("event", "shutdown");
        properties.put("restart", "NO");
        event = (ShutdownEvent) eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Returned event is of wrong type", ShutdownEvent.class,
                event.getClass());
        assertEquals("Boolean property not set correctly", Boolean.FALSE, event
                .getRestart());
    }

    public void testBuildEventWithUnregisteredEvent()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Nonexisting");
        event = eventBuilder.buildEvent(this, properties);

        assertNull(event);
    }

    public void testBuildEventWithEmptyAttributes()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        event = eventBuilder.buildEvent(this, properties);

        assertNull(event);
    }

    public void testBuildEventWithResponseEvent()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "StatusComplete");
        properties.put("actionid", "1234#origId");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Returned event is of wrong type",
                StatusCompleteEvent.class, event.getClass());
        assertEquals("ActionId not set correctly", "origId",
                ((ResponseEvent) event).getActionId());
    }

    public void testBuildEventWithSourceProperty()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Cdr");
        properties.put("source", "source value");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Src property not set correctly", "source value",
                ((CdrEvent) event).getSrc());
    }

    public void testBuildEventWithSpecialCharacterProperty()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Hangup");
        properties.put("cause-txt", "some text");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("CauseTxt property not set correctly", "some text",
                ((HangupEvent) event).getCauseTxt());
    }

    public void testBuildEventWithCidCallingPres()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Newcallerid");
        properties.put("cid-callingpres", "123 (nice description)");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("CidCallingPres property not set correctly", new Integer(123),
                ((NewCallerIdEvent) event).getCidCallingPres());
        assertEquals("CidCallingPresTxt property not set correctly", "nice description",
                ((NewCallerIdEvent) event).getCidCallingPresTxt());
    }

    public void testBuildEventWithCidCallingPresAndEmptyTxt()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Newcallerid");
        properties.put("cid-callingpres", "123 ()");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("CidCallingPres property not set correctly", new Integer(123),
                ((NewCallerIdEvent) event).getCidCallingPres());
        assertNull("CidCallingPresTxt property not set correctly (must be null)",
                ((NewCallerIdEvent) event).getCidCallingPresTxt());
    }

    public void testBuildEventWithCidCallingPresAndMissingTxt()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Newcallerid");
        properties.put("cid-callingpres", "123");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("CidCallingPres property not set correctly", new Integer(123),
                ((NewCallerIdEvent) event).getCidCallingPres());
        assertNull("CidCallingPresTxt property not set correctly (must be null)",
                ((NewCallerIdEvent) event).getCidCallingPresTxt());
    }

    public void testBuildEventWithInvalidCidCallingPres()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "Newcallerid");
        properties.put("cid-callingpres", "abc");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertNull("CidCallingPres property not set correctly (must be null)",
                ((NewCallerIdEvent) event).getCidCallingPres());
        assertNull("CidCallingPresTxt property not set correctly (must be null)",
                ((NewCallerIdEvent) event).getCidCallingPresTxt());
    }

    public void testBuildEventWithReason()
    {
        Map properties = new HashMap();
        ManagerEvent event;

        properties.put("event", "LogChannel");
        properties.put("reason", "123 - a reason");
        event = eventBuilder.buildEvent(this, properties);

        assertNotNull(event);
        assertEquals("Reason property not set correctly", new Integer(123),
                ((LogChannelEvent) event).getReason());
        assertEquals("ReasonTxt property not set correctly", "a reason", 
                ((LogChannelEvent) event).getReasonTxt());
    }
}
