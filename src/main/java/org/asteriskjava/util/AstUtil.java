package org.asteriskjava.util;

/**
 * Some static utility methods to handle Asterisk specific stuff.<p>
 * See Asterisk's <code>util.c</code>.<p>
 * Client code is not supposed to use this class.
 * 
 * @author srt
 * @version $Id$
 */
public class AstUtil
{
    // hide constructor
    private AstUtil()
    {
    }

    /**
     * Checks if a String represents <code>true</code> or <code>false</code>
     * according to Asterisk's logic.<p>
     * The original implementation is <code>util.c</code> is as follows:
     * 
     * <pre>
     *    int ast_true(const char *s)
     *    {
     *        if (!s || ast_strlen_zero(s))
     *            return 0;
     *     
     *        if (!strcasecmp(s, &quot;yes&quot;) ||
     *            !strcasecmp(s, &quot;true&quot;) ||
     *            !strcasecmp(s, &quot;y&quot;) ||
     *            !strcasecmp(s, &quot;t&quot;) ||
     *            !strcasecmp(s, &quot;1&quot;) ||
     *            !strcasecmp(s, &quot;on&quot;))
     *            return -1;
     *    
     *        return 0;
     *    }
     * </pre>
     * To support the dnd property of {@link org.asteriskjava.manager.event.ZapShowChannelsEvent}
     * this method also consideres the string "Enabled" as true. 
     * 
     * @param s the String to check for <code>true</code>.
     * @return <code>true</code> if s represents <code>true</code>,
     * <code>false</code> otherwise.
     */
    public static boolean isTrue(String s)
    {
        if (s == null || s.length() == 0)
        {
            return false;
        }

        if (("yes".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s))
                || "y".equalsIgnoreCase(s) || "t".equalsIgnoreCase(s)
                || "1".equalsIgnoreCase(s) || "on".equalsIgnoreCase(s)
                || "Enabled".equalsIgnoreCase(s))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
