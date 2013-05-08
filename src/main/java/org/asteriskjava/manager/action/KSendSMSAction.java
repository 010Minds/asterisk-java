package org.asteriskjava.manager.action;

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

import org.asteriskjava.manager.action.AbstractManagerAction;
import org.asteriskjava.manager.action.EventGeneratingAction;
import org.asteriskjava.manager.event.OriginateResponseEvent;
import org.asteriskjava.manager.event.ResponseEvent;

import java.util.*;

/**
 * 
 * Send a SMS action to Asterisk
 * 
 * @see org.asteriskjava.manager.event.OriginateResponseEvent
 */
public class KSendSMSAction extends AbstractManagerAction implements EventGeneratingAction {
    /**
     * Serializable version identifier
     */
    static final long serialVersionUID = 8194597741743334704L;

    private Integer priority;
    private Map<String, String> variables;
    private Boolean async;
    private String codecs;
    private String device;
    private String destination;
    private String message;

    /**
     * Returns the name of this action, i.e. "Originate".
     * 
     * @return the name of this action.
     */
    @Override
    public String getAction() {
	return "KSendSMS";
    }

    /**
     * Returns the priority of the extension to connect to.
     */
    public Integer getPriority() {
	return priority;
    }

    /**
     * Sets the priority of the extension to connect to. If you set the priority
     * you also have to set the context and exten properties.
     */
    public void setPriority(Integer priority) {
	this.priority = priority;
    }

    /**
     * /** Sets the variables to set on the originated call.
     * <p>
     * Variable assignments are of the form "VARNAME=VALUE". You can specify
     * multiple variable assignments separated by the '|' character.
     * <p>
     * Example: "VAR1=abc|VAR2=def" sets the channel variables VAR1 to "abc" and
     * VAR2 to "def".
     * 
     * @deprecated use {@link #setVariables(Map)} instead.
     */
    @Deprecated
    public void setVariable(String variable) {
	final StringTokenizer st;

	if (variable == null) {
	    this.variables = null;
	    return;
	}

	st = new StringTokenizer(variable, "|");
	variables = new LinkedHashMap<String, String>();
	while (st.hasMoreTokens()) {
	    String[] keyValue;

	    keyValue = st.nextToken().split("=", 2);
	    if (keyValue.length < 2) {
		variables.put(keyValue[0], null);
	    } else {
		variables.put(keyValue[0], keyValue[1]);
	    }
	}
    }

    /**
     * Sets an variable on the originated call.
     * 
     * @param name
     *            the name of the variable to set.
     * @param value
     *            the value of the variable to set.
     * @since 0.3
     */
    public void setVariable(String name, String value) {
	if (variables == null) {
	    variables = new LinkedHashMap<String, String>();
	}

	variables.put(name, value);
    }

    /**
     * Returns the variables to set on the originated call.
     * 
     * @return a Map containing the variable names as key and their values as
     *         value.
     * @since 0.2
     */
    public Map<String, String> getAttributes() {
	return variables;
    }

    /**
     * Sets the variables to set on the originated call.
     * 
     * @param variables
     *            a Map containing the variable names as key and their values as
     *            value.
     * @since 0.2
     */
    public void setVariables(Map<String, String> variables) {
	this.variables = variables;
    }

    /**
     * Returns true if this is a fast origination.
     */
    public Boolean getAsync() {
	return async;
    }

    /**
     * Set to true for fast origination. Only with fast origination Asterisk
     * will send OriginateSuccess- and OriginateFailureEvents.
     */
    public void setAsync(Boolean async) {
	this.async = async;
    }

    /**
     * Returns the codecs to use for the call.
     * 
     * @return the codecs to use for the call.
     * @since 1.0.0
     */
    public String getCodecs() {
	return codecs;
    }

    /**
     * Sets the codecs to use for the call. For example "alaw, ulaw, h264".
     * <p>
     * Available since Asterisk 1.6.
     * 
     * @param codecs
     *            comma separated list of codecs to use for the call.
     * @since 1.0.0
     */
    public void setCodecs(String codecs) {
	this.codecs = codecs;
    }

    /**
     * Sets the codecs to use for the call.
     * <p>
     * Available since Asterisk 1.6.
     * 
     * @param codecs
     *            list of codecs to use for the call.
     * @since 1.0.0
     */
    public void setCodecs(List<String> codecs) {
	if (codecs == null || codecs.isEmpty()) {
	    this.codecs = null;
	    return;
	}

	Iterator<String> iter = codecs.iterator();
	StringBuffer buffer = new StringBuffer(iter.next());
	while (iter.hasNext()) {
	    buffer.append(",").append(iter.next());
	}
	this.codecs = buffer.toString();
    }

    public Class<? extends ResponseEvent> getActionCompleteEventClass() {
	return OriginateResponseEvent.class;
    }

    /**
     * @param device
     *            the device to set
     */
    public void setDevice(String device) {
	this.device = device;
    }

    /**
     * @return the device
     */
    public String getDevice() {
	return device;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(String destination) {
	this.destination = destination;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
	return destination;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
	this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }
}
