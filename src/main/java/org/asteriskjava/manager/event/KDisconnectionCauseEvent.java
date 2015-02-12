package org.asteriskjava.manager.event;

/**
 * A DisconnectionCauseEvent is triggered when a channel is disconnected.
 * <p>
 * It is implemented by Khomp Driver
 * 
 * @author rdt
 * @version $Id$
 */
public class KDisconnectionCauseEvent extends AbstractChannelEvent {
    /**
     * Serializable version identifier.
     */
    static final long serialVersionUID = 0L;

    private Integer cause;
    private String causeTxt;
    private String destAddr;

    private String origAddr;

    public KDisconnectionCauseEvent(Object source) {
	super(source);
    }

    /**
     * Returns the cause of the disconnection.
     * 
     * @return the disconnection cause.
     */
    public Integer getCause() {
	return cause;
    }

    /**
     * Sets the cause of the disconnection.
     * 
     * @param cause
     *            the disconnection cause.
     */
    public void setCause(Integer cause) {
	this.cause = cause;
    }

    /**
     * Returns the textual representation of the disconnection cause.
     * 
     * @return the textual representation of the disconnection cause.
     * @since 1.0
     */
    public String getCauseTxt() {
	return causeTxt;
    }

    /**
     * Sets the textual representation of the disconnection cause.
     * 
     * @param causeTxt
     *            the textual representation of the disconnection cause.
     */
    public void setCauseTxt(String causeTxt) {
	this.causeTxt = causeTxt;
    }

    /**
     * Returns the Caller*ID number of the channel connected if set. If the
     * channel has no caller id set "unknown" is returned.
     * 
     */
    public String getDestAddr() {
	return destAddr;
    }

    public void setDestAddr(String destAddr) {
	this.destAddr = destAddr;
    }
    /**
    * Returns the Caller*ID number of the channel connected if set. If the
    * channel has no caller id set "unknown" is returned.
    * 
    */
   public String getOrigAddr() {
	return origAddr;
   }

   public void setOrigAddr(String origAddr) {
	this.origAddr = origAddr;
   }
}
