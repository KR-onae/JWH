package com.kronae.jwh.request;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public interface JWHRequest {
    URI getURI();
    InetSocketAddress getAddress();
    String readData() throws IOException;
    Headers getHeader();
    String  getMethod();
    Object  getAttribute(String key);
    HttpPrincipal getPrincipal();
    String getProtocol();
    @Contract(value = "_ -> new", pure = true)
    static @NotNull JWHRequest fromExchange(HttpExchange exchange) {
        return new JWHRequest() {
            private String requestBody = null;
            @Override
            public URI getURI() {
                return exchange.getRequestURI();
            }

            @Override
            public InetSocketAddress getAddress() {
                return exchange.getLocalAddress();
            }

            @Override
            public String readData() {
                try {
                    if(requestBody == null) {
                        InputStream stream = exchange.getRequestBody();
                        if (stream == null) return null;

                        String buf = "";
                        while (stream.available() > 0)
                            buf += (char) stream.read();

                        stream.close();
                        requestBody = buf;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return requestBody;
            }

            @Override
            public Headers getHeader() {
                return exchange.getRequestHeaders();
            }

            @Override
            public String getMethod() {
                return exchange.getRequestMethod();
            }

            @Override
            public Object getAttribute(String key) {
                return exchange.getAttribute(key);
            }

            @Override
            public HttpPrincipal getPrincipal() {
                return exchange.getPrincipal();
            }

            @Override
            public String getProtocol() {
                return exchange.getProtocol();
            }
        };
    }
}
