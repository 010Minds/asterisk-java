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
package org.asteriskjava.live.internal;

import org.asteriskjava.live.AsteriskQueue;
import org.asteriskjava.live.AsteriskQueueMember;
import org.asteriskjava.live.QueueMemberState;

/**
 * Default implementation of a queue member.
 *
 * @author <a href="mailto:patrick.breucking{@nospam}gonicus.de">Patrick Breucking</a>
 * @version $Id$
 * @see AsteriskQueueMember
 * @since 0.3.1
 */
class AsteriskQueueMemberImpl extends AbstractLiveObject implements AsteriskQueueMember
{
    private AsteriskQueue queue;
    private QueueMemberState state;
    private String location;
    private Integer penalty;

    /**
     * Creates a new queue member.
     *
     * @param server   server this channel belongs to.
     * @param queue    queue this member is registered to.
     * @param location location of member.
     * @param state    state of this member.
     * @param penalty  penalty of this member.
     */
    AsteriskQueueMemberImpl(final AsteriskServerImpl server,
                            final AsteriskQueueImpl queue, String location,
                            QueueMemberState state, Integer penalty)
    {
        super(server);
        this.queue = queue;
        this.location = location;
        this.state = state;
    }

    public AsteriskQueue getQueue()
    {
        return queue;
    }

    public String getLocation()
    {
        return location;
    }

    public QueueMemberState getState()
    {
        return state;
    }

    public Integer getPenalty()
    {
        return penalty;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb;

        sb = new StringBuffer("AsteriskQueueMember[");
        sb.append("location='").append(location).append("'");
        sb.append("state='").append(state).append("'");
        sb.append("queue='").append(queue.getName()).append("'");
        sb.append("systemHashcode=").append(System.identityHashCode(this));
        sb.append("]");

        return sb.toString();
    }

    /**
     * Notifies all PropertyChangeListener that the state of this member has
     * changed.
     *
     * @param state - The new state.
     */
    synchronized void stateChanged(QueueMemberState state)
    {
        QueueMemberState oldState = this.state;
        this.state = state;
        firePropertyChange(PROPERTY_STATE, oldState, state);
    }

    synchronized void penaltyChanged(Integer penalty)
    {
        Integer oldPenalty = this.penalty;
        this.penalty = penalty;
        firePropertyChange(PROPERTY_PENALTY, oldPenalty, penalty);
    }
}
