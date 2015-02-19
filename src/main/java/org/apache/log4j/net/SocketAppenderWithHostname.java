package org.apache.log4j.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.net.SocketAppender;
import org.apache.log4j.spi.LoggingEvent;

public class SocketAppenderWithHostname extends SocketAppender {

    public final static String DEFAULT_MDC_KEY_FOR_HOSTNAME = "real_source_host";
    
    private String mdcKeyForHostname = DEFAULT_MDC_KEY_FOR_HOSTNAME;

    private String hostname;

    @Override
    public void append(LoggingEvent event) {
        addHostnameToEvent(event);
        super.append(event);
    }

    protected void addHostnameToEvent(LoggingEvent event) {
        if (event == null) {
            return;
        }
        event.setProperty(getMdcKeyForHostname(), getHostname());
    }

    public String getHostname() {
        if (hostname == null) {
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                hostname = "unknown";
            }
        }
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMdcKeyForHostname() {
        return mdcKeyForHostname;
    }

    public void setMdcKeyForHostname(String mdcFieldForHostname) {
        this.mdcKeyForHostname = mdcFieldForHostname;
    }
}
