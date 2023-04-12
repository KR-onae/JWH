import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
public class HttpServerManager {
    /*//\/ OPTIONS /\//*/
    private final String HOSTNAME = "0.0.0.0";
    private final int PORT = 80;
    private final int BACKLOG = 0;


    private HttpServer server = null;
    private Console console = null;
    private boolean IsOpen = false;

    public HttpServerManager(Console c) {
        console = c;
        console.println("HttpServerManager/HttpServerManager(Console)", "Creating HTTP(web) server...");
        createServer(HOSTNAME, PORT);
    }
    public HttpServerManager(Console c, int port) {
        console = c;
        console.println("HttpServerManager/HttpServerManager(Console, int)", "Creating HTTP(web) server with port " + port + "...");
        createServer(HOSTNAME, port);
    }
    public HttpServerManager(Console c, String host, int port) {
        console = c;
        console.println("HttpServerManager/HttpServerManager(Console, String, int)", "Creating HTTP(web) server with " + host + ":" + port + "...");
        createServer(host, port);
    }

    private void createServer(String host, int port) {
        //HTTP SERVER 생성
        try {
            this.server = HttpServer.create(new InetSocketAddress(host, port),BACKLOG);
        } catch (IOException e) {
            console.println("HttpServerManager/createServer(String, int)", "An IOException error occurred.");
            e.printStackTrace();
        }
    }
    public void createContext(String p, Handler h) {
        server.createContext(p, new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                h.handle(exchange);
            }
        });
    }

    public boolean isOpen() {
        return IsOpen;
    }
    public HttpServer getServer() {
        return server;
    }
    public HttpServer start() {
        server.start();
        console.println("HttpServerManager/start()", "Server open.");
        IsOpen = true;
        return server;
    }
    public void stop(int delay) {
        server.stop(delay);
        console.println("HttpServerManager/start()", "Server stop.");
        IsOpen = false;
    }
}