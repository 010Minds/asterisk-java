/*
 *  Copyright 2013 010Minds
 *
 */
package org.asteriskjava.manager.event;

/**
 * A NewSMSConfirmation is triggered when receives a SMS Confirmation.<p>
 * It is implemented in <code>channel.c</code>
 * 
 * @author srt
 * @version $Id$
 */
public class NewSMSConfirmation extends ManagerEvent
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
     * The date SMS was delivered
     */
    private String deliveryDate;
    
    private String status;
    
    public NewSMSConfirmation(Object source)
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
     * @return the deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
