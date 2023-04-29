package com.kronae.jwh.event.event;

import com.kronae.jwh.JWH;
import com.kronae.jwh.event.JWHEvent;

public class JWHEvent_ServerStopEvent implements JWHEvent {
    private final JWH web;
    public JWHEvent_ServerStopEvent(JWH web) {
        this.web = web;
    }
    public JWH getWeb() {
        return web;
    }
}