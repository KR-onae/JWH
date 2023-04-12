package com.kronae.jwh.response;

import com.kronae.jwh.status.JWHStatusCode;
import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface JWHResponse {
    void write(String buf) throws IOException;
    void end(JWHStatusCode code) throws IOException;
    void end(int statusCode) throws IOException;
    JWHResponseHeader header();
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull JWHResponse fromExchange(HttpExchange exchange, Charset charset) {
        return new JWHResponse() {
            private String buf = "";
            @Override
            public void write(String buf) {
                this.buf += buf;
            }

            @Override
            public void end(JWHStatusCode code) throws IOException {
                OutputStream responseBody = exchange.getResponseBody();

                try {
                    ByteBuffer b = charset.encode(buf);
                    int len = b.limit();
                    byte[] content = new byte[len];
                    b.get(content, 0, len);

                    exchange.sendResponseHeaders(code.code(), len);

                    responseBody.write(content);
                    responseBody.close();

                    exchange.close();
                } catch(IOException e) {
                    if( responseBody != null ) {
                        try {
                            responseBody.close();
                        } catch(IOException ignored) {
                        }
                    }
                    throw e;
                }
            }

            @Override
            public void end(int statusCode) throws IOException {
                end(new JWHStatusCode(statusCode));
            }

            @Override
            public JWHResponseHeader header() {
                return JWHResponseHeader.fromExchange(exchange);
            }
        };
    }
}
