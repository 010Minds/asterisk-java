package org.asteriskjava.fastagi;


import org.apache.log4j.Logger;
import org.asteriskjava.live.DefaultAsteriskServer;

import java.io.IOException;
import java.net.InetAddress;

public class HangupTest extends BaseAgiScript
{
    private Logger logger = Logger.getLogger(HangupTest.class);

    public void service(AgiRequest request, AgiChannel channel) throws AgiException
    {
        System.out.println(request.getParameterMap());
        answer();

        try
        {
            for (int i = 0; i < 5000; i++)
            {
                System.out.println("Saying " + i);
                sayDigits(String.valueOf(i));
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            logger.info("Exception caught: " + e.getMessage(), e);
        }

    }

    public static void main(String[] args) throws IOException
    {
        AgiServerThread agiServerThread = new AgiServerThread();
        agiServerThread.setAgiServer(new DefaultAgiServer());
        agiServerThread.setDaemon(false);
        agiServerThread.startup();

        DefaultAsteriskServer server = new DefaultAsteriskServer("pbx0", "manager", "obelisk");
        server.initialize();
        server.originateToApplication("SIP/phone-02", "AGI",
                "agi://" + InetAddress.getLocalHost().getHostAddress() + "/" + HangupTest.class.getName()
                + ", arg1,arg2", 30000);
    }
}