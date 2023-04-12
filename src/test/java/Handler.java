import com.sun.net.httpserver.HttpExchange;

public interface Handler {
    void handle(HttpExchange exchange);
}
