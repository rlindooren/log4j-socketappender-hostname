This extension of Log4J's regular SocketAppender adds the host name to the logging event before sending it to a SocketServer.
When a SocketServer receives events from multiple hosts this allows to track on which host the event took place.