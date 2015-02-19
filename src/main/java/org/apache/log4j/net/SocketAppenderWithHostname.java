package org.apache.log4j.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.spi.LoggingEvent;

public class SocketAppenderWithHostname extends SocketAppender {

    public final static String DEFAULT_MDC_KEY_FOR_HOSTNAME = "real_source_host";

    private String mdcKeyForHostname = DEFAULT_MDC_KEY_FOR_HOSTNAME;

    private String hostname;

    private Map<String, String> extraMdcValues = new HashMap<String, String>();

    @Override
    public void append(LoggingEvent event) {
        addHostnameToEvent(event);
        addExtraMdcValuesToEvent(event);
        super.append(event);
    }

    protected void addHostnameToEvent(LoggingEvent event) {
        if (event == null) {
            return;
        }
        event.setProperty(getMdcKeyForHostname(), getHostname());
    }
    
    protected void addExtraMdcValuesToEvent(LoggingEvent event){
        if (event == null) {
            return;
        }
        for (String key : extraMdcValues.keySet()){
            String value = extraMdcValues.get(key);
            if (value != null) {
                event.setProperty(key, value);
            }
        }
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

    public void setExtraMdcValues(String concatenatedMdcKeyValuePairs){
        if (concatenatedMdcKeyValuePairs == null){
            return;
        }
        String[] splittedKeyValuePairs = concatenatedMdcKeyValuePairs.split(",");
        for (String kvp : splittedKeyValuePairs){
            String[] keyValue = kvp.split("=");
            extraMdcValues.put(keyValue[0], keyValue[1]);
        }
    }
}
