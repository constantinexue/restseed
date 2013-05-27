package constantinexue.restseed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.google.inject.servlet.GuiceFilter;

import constantinexue.restseed.guice.GuiceServletListener;
import constantinexue.restseed.util.Configuration;
import constantinexue.restseed.util.LibraryLoader;

public class Launcher {
    
    private static final String START_COMMAND = "start";
    private static final String STOP_COMMAND = "stop";
    private static final int HTTP_PORT = 3990;
    private static final int STOP_PORT = 3991;
    
    // Logger.getLogger gets the weak reference. It must be kept here, or the setting is invalid.
    private static java.util.logging.Logger jerseyLogger;
    
    private static Server jettyServer;
    
    public static void main(String[] args) {
        String command;
        if (args.length > 0) {
            command = args[0].toLowerCase();
        }
        else {
            command = START_COMMAND;
        }
        if (command.equals(START_COMMAND)) {
            start();
        }
        else if (command.equals(STOP_COMMAND)) {
            stop();
        }
        else {
            System.err.println("Unknown command: " + command);
            return;
        }
    }
    
    public static void start() {
        System.out.println("Starting ...");
        
        // Resets the log level of jersey, starting from WARNING.
        jerseyLogger = java.util.logging.Logger.getLogger("com.sun.jersey");
        jerseyLogger.setLevel(java.util.logging.Level.WARNING);
        
        // Loads configurations from files.
        try {
            Configuration.loadLog4j("./conf/log4j.xml");
            Configuration.loadProperties("./conf/application.properties");
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        // Loads native libraries from defined folder.
        try {
            LibraryLoader.loadAll();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        jettyServer = createJettyServer();
        try {
            jettyServer.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        // Starts the stop signal monitor thread.
        try {
            new MonitorThread().start();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        System.out.println("Started, the server is running on http://localhost:" + HTTP_PORT);
        // If doesn't "join" here, the end of main thread won't cause program exit.
        // BUT, we should do.
        try {
            jettyServer.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
    
    public static void stop() {
        System.out.println("Stopping ...");
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), STOP_PORT);
            OutputStream out = socket.getOutputStream();
            out.write("\r\n".getBytes());
            out.flush();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    
    private static Server createJettyServer() {
        Server server = new Server(HTTP_PORT);
        // Sets the hook for JVM termination.
        server.setStopAtShutdown(true);
        
        // The RESTful API doesn't need session support.
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        GuiceServletListener listener = new GuiceServletListener();
        context.addEventListener(listener);
        // CORS supporting. http://www.w3.org/TR/cors/
        // Doesn't support IE. Use JSONP or Nginx proxy to solve this issue.
        context.addFilter(CrossOriginFilter.class, "/*", null);
        context.addFilter(GuiceFilter.class, "/*", null);
        ServletHolder servletHolder = new ServletHolder(DefaultServlet.class);
        context.addServlet(servletHolder, "/*");
        server.setHandler(context);
        
        return server;
    }
    
    /**
     * Implements a graceful shutdown for jetty.
     * Occupies a port to listen stop signal, then shutdown server.
     */
    public static class MonitorThread extends Thread {
        
        private ServerSocket socket;
        
        public MonitorThread() {
            setDaemon(true);
            setName("StopMonitor");
            try {
                socket = new ServerSocket(STOP_PORT, 1, InetAddress.getByName("127.0.0.1"));
            }
            catch (Exception e) {
                System.exit(-1);
            }
        }
        
        @Override
        public void run() {
            Socket accept;
            try {
                accept = socket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                reader.readLine();
                // Leaves 10 seconds for cleaning up executing requests.
                jettyServer.setGracefulShutdown(10000);
                accept.close();
                reader.close();
                socket.close();
                System.out.println("Server is stopped.");
            }
            catch (Exception e) {
                System.exit(-1);
            }
            System.exit(0);
        }
    }
}
