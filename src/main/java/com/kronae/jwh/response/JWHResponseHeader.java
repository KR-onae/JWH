package com.kronae.jwh.response;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface JWHResponseHeader {
    JWHResponseHeader add(String key, String value);
    JWHResponseHeader put(String key, List<String> values);
    List<String> get(String key);
    String getFirst(String key);
    @Contract(value = "_ -> new", pure = true)
    static @NotNull JWHResponseHeader fromExchange(HttpExchange exchange) {
        return new JWHResponseHeader() {
            private final Headers header = exchange.getResponseHeaders();
            public JWHResponseHeader add(String key, String value) {
                header.add(key, value);
                return this;
            }
            public JWHResponseHeader put(String key, List<String> values) {
                header.put(key, values);
                return this;
            }
            public List<String> get(String key) {
                return header.get(key);
            }
            public String getFirst(String key) {
                return header.getFirst(key);
            }
        };
    }
}
