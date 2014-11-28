package org.apache.log4j.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.net.SocketAppender;
import org.apache.log4j.spi.LoggingEvent;

public class SocketAppenderWithHostname extends SocketAppender {

    public final static String DEFAULT_MDC_KEY_FOR_HOSTNAME = "real_source_host";
    
    private String mdcKeyForHostname = DEFAULT_MDC_KEY_FOR_HOSTNAME;

    private String hostName;

    @Override
    public void append(LoggingEvent event) {
        addHostnameToEvent(event);
        super.append(event);
    }

    protected void addHostnameToEvent(LoggingEvent event) {
        if (event == null) {
            return;
        }
        event.setProperty(getMdcKeyForHostname(), getHostName());
    }

    public String getHostName() {
        if (hostName == null) {
            try {
                hostName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                hostName = "unknown";
            }
        }
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getMdcKeyForHostname() {
        return mdcKeyForHostname;
    }

    public void setMdcKeyForHostname(String mdcFieldForHostname) {
        this.mdcKeyForHostname = mdcFieldForHostname;
    }
}
