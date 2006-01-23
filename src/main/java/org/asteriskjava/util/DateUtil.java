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
package org.asteriskjava.util;

import java.util.Date;

/**
 * Utility class to obtain the current date and allows to override with a fixed value for testing.
 * 
 * @author srt
 * @version $Id: DateUtil.java,v 1.1 2005/03/11 15:31:47 srt Exp $
 */
public class DateUtil
{
    private static Date currentDate;

    private DateUtil()
    {
    }

    /**
     * If set to a non null value uses the date given as current date on calls to getDate(). Set to
     * null to restore the normal behavior.
     * 
     * @param currentDate the date to return on calls to getDate() or <code>null</code> to return
     * the real current date.
     */
    public static void overrideCurrentDate(Date currentDate)
    {
        DateUtil.currentDate = currentDate;
    }

    /**
     * Returns the real current date or the date set with overrideCurrentDate().
     * 
     * @return the real current date or the date set with overrideCurrentDate().
     */
    public static Date getDate()
    {
        if (currentDate != null)
        {
            return currentDate;
        }
        else
        {
            return new Date();
        }
    }
}
