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
package net.sf.asterisk.util.impl;

import net.sf.asterisk.util.Log;

/**
 * A Log implementation that does nothing.
 * 
 * @author srt
 * @version $Id: NullLog.java,v 1.1 2005/04/20 18:22:13 srt Exp $
 */
public class NullLog implements Log
{
    /**
     * Creates a new NullLog.
     */
    public NullLog()
    {

    }

    public void debug(Object obj)
    {
    }

    public void info(Object obj)
    {
    }

    public void warn(Object obj)
    {
    }

    public void warn(Object obj, Throwable ex)
    {
    }

    public void error(Object obj)
    {
    }

    public void error(Object obj, Throwable ex)
    {
    }
}
