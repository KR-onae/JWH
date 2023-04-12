package com.kronae.jwh.util.api;

import com.kronae.json.JSONObject;
import com.kronae.jwh.JWH;
import com.kronae.jwh.event.JWHEvent;
import com.kronae.jwh.event.JWHEventHandler;
import com.kronae.jwh.event.JWHEventListener;
import com.kronae.jwh.event.event.JWHEvent_ServerOpenEvent;

public class JWHLoginApi implements JWHApi {
    private JSONObject loginUsers;
    @Override
    public void setup(JWH web) {
        web.addEventListener(new JWHEventListener() {
            @JWHEventHandler
            public void onOpen(JWHEvent_ServerOpenEvent event) {
                System.out.println("1231231234132");
            }
        });
    }
}