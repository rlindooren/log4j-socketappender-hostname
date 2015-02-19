package org.apache.log4j.net;

import static org.junit.Assert.*;

import java.net.InetAddress;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketAppenderWithHostname;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class SocketAppenderWithHostnameTest {

    @Test
    public void testAddHostnameToEvent() throws Exception {
        SocketAppenderWithHostname appender = new SocketAppenderWithHostname();

        LoggingEvent loggingEvent = new LoggingEvent("", Logger.getLogger("foo"), Level.INFO, "Test message", null);
        assertNull(loggingEvent.getMDC(SocketAppenderWithHostname.DEFAULT_MDC_KEY_FOR_HOSTNAME));

        appender.addHostnameToEvent(loggingEvent);

        assertEquals(InetAddress.getLocalHost().getHostName(), loggingEvent.getMDC(SocketAppenderWithHostname.DEFAULT_MDC_KEY_FOR_HOSTNAME));
    }

    @Test
    public void testAddHostnameToEventWithCustomValues() throws Exception {
        final String mdcKey = "original_host";
        final String hostName = "node1.example.com";

        SocketAppenderWithHostname appender = new SocketAppenderWithHostname();
        appender.setMdcKeyForHostname(mdcKey);
        appender.setHostname(hostName);

        LoggingEvent loggingEvent = new LoggingEvent("", Logger.getLogger("foo"), Level.INFO, "Test message", null);
        assertNull(loggingEvent.getMDC(SocketAppenderWithHostname.DEFAULT_MDC_KEY_FOR_HOSTNAME));
        assertNull(loggingEvent.getMDC(mdcKey));

        appender.addHostnameToEvent(loggingEvent);

        assertNull(loggingEvent.getMDC(SocketAppenderWithHostname.DEFAULT_MDC_KEY_FOR_HOSTNAME));
        assertEquals(hostName, loggingEvent.getMDC(mdcKey));
    }
}
