package demo;

import org.apache.log4j.net.SimpleSocketServer;

public class Server {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting " + SimpleSocketServer.class.getSimpleName());
        final String pathToConfig = Server.class.getResource("/log4j-demo-server.properties").getFile();
        SimpleSocketServer.main(new String[] {"4560", pathToConfig});
    }
}
