/*
 *  Copyright 2013 010Minds
 *
 */
package org.asteriskjava.manager.event;

/**
 * A NewSMSConfirmation is triggered when receives a SMS.<p>
 * It is implemented in <code>channel.c</code>
 * 
 * @author srt
 * @version $Id$
 */
public class NewSMS extends ManagerEvent
{ 
    
    /**
     * Serializable version identifier.
     */
    static final long serialVersionUID = -0L;
    
    /**
     * The name of the source channel.
     */
    private String channel;

    /**
     * The name of the destination channel.
     */
    private String from;

    /**
     * The date SMS was sent
     */
    private String date;

    /**
     * The SMS size
     */
    private String size;

    /**
     * The SMS codification
     */
    private String mode;

    /**
     * The SMS message
     */
    private String message;


    public NewSMS(Object source)
    {
        super(source);
    }


    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }


    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }


    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }


    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }


    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }


    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }


    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }


    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }


    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }


    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }


    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
}
