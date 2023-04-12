package com.kronae.jwh.util;

import com.kronae.jwh.JWH;
import com.kronae.jwh.request.JWHRequest;
import com.kronae.jwh.request.JWHRequestHandler;
import com.kronae.jwh.response.JWHResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWHEasy extends JWH {
    private final HashMap<String, JWHRequestHandler> handlers;
    public JWHEasy(int port) {
        super(port);
        handlers = new HashMap<>();
        addHandler(new JWHRequestHandler() {
            @Override
            public void handle(JWHRequest req, JWHResponse res) throws IOException {
                handlers.forEach((path, handler) -> {
                    if(req.getURI().getPath().equals(path)) {
                        try {
                            handler.handle(req, res);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
    public JWHEasy setHandlerFromPath(String path, JWHRequestHandler handler) {
        handlers.put(path, handler);
        return this;
    }
}
