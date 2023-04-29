package com.kronae.jwh.event.event;

import com.kronae.jwh.JWH;
import com.kronae.jwh.event.JWHEvent;
import com.kronae.jwh.request.JWHRequest;

public class JWHEvent_RequestEvent implements JWHEvent {
    private final JWHRequest req;
    public JWHEvent_RequestEvent(JWHRequest req) {
        this.req = req;
    }
    public JWHRequest getRequest() {
        return req;
    }
}