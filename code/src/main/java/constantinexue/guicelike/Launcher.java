package constantinexue.guicelike;

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

import constantinexue.guicelike.guice.GuiceServletListener;
import constantinexue.guicelike.util.Configuration;

public class Launcher {
    
    // Logger.getLogger获得的是弱引用，如果不保存，设置就没有效果。
    private static java.util.logging.Logger jerseyLogger;
    
    private static final String START_COMMAND = "start";
    private static final String STOP_COMMAND = "stop";
    
    private static final int HTTP_PORT = 8080;
    private static final int STOP_PORT = 8081;
    private static Server jettyServer;
    
    public static void main(String[] args) {
        String command;
        if (args.length > 0) {
            command = args[0].toLowerCase();
        }
        else {
            command = "start";
        }
        if (command.equals(START_COMMAND)) {
            executeStartCommand();
        }
        else if (command.equals(STOP_COMMAND)) {
            executeStopCommand();
        }
        else {
            System.err.println("Unknown command: " + command);
            return;
        }
    }
    
    private static void executeStartCommand() {
        System.out.println("Starting ...");
        
        // 重设jersey的Log输入等级，不需要info，只需要warning。
        jerseyLogger = java.util.logging.Logger.getLogger("com.sun.jersey");
        jerseyLogger.setLevel(java.util.logging.Level.WARNING);
        
        // 读取配置
        try {
            Configuration.loadLog4j("./conf/log4j.xml");
            Configuration.loadProperties("./conf/application.properties");
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
        
        // 开启终止命令的监控线程。
        try {
            new MonitorThread().start();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        System.out.println("Started, the server is running on http://localhost:" + HTTP_PORT);
        // 如果不join，主线程结束也不会引起程序退出。
        try {
            jettyServer.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
    
    private static void executeStopCommand() {
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
        //设置在JVM退出时关闭Jetty的钩子。
        server.setStopAtShutdown(true);
        
        // REST风格的API不需要session。
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        GuiceServletListener listener = new GuiceServletListener();
        context.addEventListener(listener);
        // 支持跨域
        context.addFilter(CrossOriginFilter.class, "/*", null);
        context.addFilter(GuiceFilter.class, "/*", null);
        ServletHolder servletHolder = new ServletHolder(DefaultServlet.class);
        context.addServlet(servletHolder, "/*");
        server.setHandler(context);
        
        return server;
    }
    
    /**
     * 用于实现jetty的graceful shutdown。
     * 占用一个端口，监控stop指令，然后关闭server。
     * 
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
                // 预留10秒钟让正在运行的请求处理结束。
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
