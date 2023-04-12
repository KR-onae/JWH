package com.kronae.jwh.request;

import com.kronae.jwh.response.JWHResponse;

import java.io.IOException;

public interface JWHRequestHandler {
    void handle(JWHRequest req, JWHResponse res) throws IOException;
}
