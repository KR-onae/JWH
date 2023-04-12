package com.kronae.jwh.event.event;

import com.kronae.jwh.JWH;
import com.kronae.jwh.event.JWHEvent;

public class JWHEvent_ServerOpenEvent implements JWHEvent {
    private JWH web;
    public JWHEvent_ServerOpenEvent(JWH web) {
        this.web = web;
    }
    public JWH getWeb() {
        return web;
    }
}