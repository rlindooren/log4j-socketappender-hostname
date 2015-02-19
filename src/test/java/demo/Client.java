package demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.Priority;

public class Client {

    public static void main(String[] args) throws Exception {

        System.setProperty("log4j.configuration", "log4j-demo-client.properties");

        final Logger logger = Logger.getLogger(Client.class);

        final int nrOfLoggers = 3;

        System.out.println("Starting " + nrOfLoggers + " random loggers");

        final String[] users = {"somebody@example.com", "nobody@example.org"};
        final ExecutorService executorService = Executors.newFixedThreadPool(nrOfLoggers);

        for (int i = 0; i < nrOfLoggers; i++) {

            executorService.submit(new Runnable() {

                @SuppressWarnings("deprecation")
                public void run() {
                    System.out.println("<" + Thread.currentThread().getName() + "> starting");

                    Random r = new Random();
                    int messageNr = 0;
                    while (true) {
                        messageNr++;
                        try {
                            Thread.sleep(r.nextInt(10) * 1000);

                            MDC.clear();

                            MDC.put("user", users[r.nextInt(users.length)]);

                            /*
                             * Priority.FATAL_INT = 50000;
                             * Priority.ERROR_INT = 40000;
                             * Priority.WARN_INT  = 30000;
                             * Priority.INFO_INT  = 20000;
                             * Priority.DEBUG_INT = 10000;
                             */
                            Priority priority = Priority.toPriority((r.nextInt(5) + 1) * 10000);

                            String message = "This is message nr. " + messageNr;

                            if (priority.isGreaterOrEqual(Priority.ERROR)) {
                                logger.log(priority, message, new RuntimeException("For stacktrace purpose"));
                                // logger.error(message, new Exception("For stacktrace purpose"));
                            } else {
                                logger.log(priority, message);
                            }

                            System.out.println("<" + Thread.currentThread().getName() + "> logged message: '" + message + "' on level " + priority);
                        } catch (Exception ex) {
                            logger.fatal("Interupted while sleeping");
                            break;
                        }
                    }
                }
            });
        }
    }
}
