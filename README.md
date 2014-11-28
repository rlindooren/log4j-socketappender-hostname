This extension of Log4J's regular SocketAppender adds the host name to the logging event before sending it to a SocketServer.
When a SocketServer receives events from multiple hosts this allows to track on which host the event took place.

###How to use

You basically use the SocketAppenderWithHostname instead of the regular SocketAppender

An example Log4J configuration (log4j.properties):
```INI
log4j.rootLogger=ALL, server
#log4j.appender.server=org.apache.log4j.net.SocketAppender
log4j.appender.server=org.apache.log4j.net.SocketAppenderWithHostname
log4j.appender.server.Port=12345
log4j.appender.server.RemoteHost=your.logging.host
log4j.appender.server.ReconnectionDelay=10000
log4j.appender.server.LocationInfo=true
log4j.appender.server.Application=theNameOfYourApplication
```

###### Existing webapps deployed on Tomcat

When deployed on Tomcat, it is possible to use this appender without having to modify existing webapps.

For this you can create a virtual class path containing a `log4j.properties` file that will be read instead of the Log4J configuration provided by the webapp itself.
>E.g.: *${catalina.base}*/virtualClasspaths/theNameOfYourWebapp/

Next to this, you also create a generic virtual class path directory that will contain `log4j-socketappender-hostname.jar`
>E.g.: *${catalina.base}*/virtualClasspaths/generic/

Finally you create a Context file for your webapp in `${catalina.base}/conf/Catalina/localhost' with the name of your webapp.
>E.g.: *${catalina.base}*/conf/Catalina/localhost/theNameOfYourWebapp.xml

With these contents:

```XML
<Context>
  <!-- don't forget to point to the correct virtual class path you created for your webapp -->
  <Loader
      className="org.apache.catalina.loader.VirtualWebappLoader"
      virtualClasspath="${catalina.base}/virtualClassPaths/theNameOfYourWebapp;${catalina.base}/virtualClassPaths/generic/log4j-socketappender-hostname.jar"
      searchVirtualFirst="true"/>
</Context>
```
