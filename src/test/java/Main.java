import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.util.Scanner;

public class Main {
    private static HttpServerManager HSM = null;
    private static Console console = new Console();
    private static HttpServer server = null;
    private static Scanner scanner = null;

    public static void main(String[] args) {
        //서버 생성
        HSM = new HttpServerManager(console, "localhost",80);
        HSM.createContext("/", new Handler() {
            @Override
            public void handle(HttpExchange exchange) {
                System.out.println(exchange.getRemoteAddress().getAddress());
            }
        });
        server = HSM.start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(HSM.isOpen()) {
                    console.println("Main/main", "Auto stop.");
                    HSM.stop(0);
                }
            }
        }));

        console.println("Main/main", "Write 'stop' command to stop server.");
        scanner = new Scanner(System.in);
        String temp;
        Action output;
        while(true) {
            temp = scanner.nextLine();
            output = Command.run(temp, console);
            if(output == Action.ERR_UNKNOWN_COMMAND) {
                console.println("Command/run-Main/main", "ERROR: Unknown command.");
            } else if(output == Action.ACT_SERVER_STOP) {
                if(HSM.isOpen()) {
                    HSM.stop(0);
                } else {
                    console.println("Main/main", "ERROR: The server is already close.");
                }
            } else if(output == Action.ACT_SERVER_OPEN) {
                if(HSM.isOpen()) {
                    console.println("Main/main", "ERROR: The server is already open.");
                } else {
                    HSM.start();
                }
            } else if(output == Action.ACT_SYSTEM_EXIT) {
                console.println("Command/run-Main/main", "Exit...");
                System.exit(0);
            }
        }
    }
}
