package com.kronae.jwh.util.api;

import com.kronae.jwh.event.JWHEventHandler;
import com.kronae.jwh.event.JWHEventListener;
import com.kronae.jwh.event.event.JWHEvent_ServerOpenEvent;

public class JWHLoginApi_EventListener implements JWHEventListener {
    public JWHLoginApi_EventListener() {}
    @JWHEventHandler
    public void onOpen(JWHEvent_ServerOpenEvent event) {
        System.out.println("1231231234132");
    }
}
