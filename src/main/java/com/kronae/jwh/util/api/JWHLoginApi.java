package com.kronae.jwh.util.api;

import com.kronae.jwh.JWH;
import com.kronae.jwh.event.JWHEventHandler;
import com.kronae.jwh.event.JWHEventListener;
import com.kronae.jwh.event.event.JWHEvent_ServerStartEvent;
import com.kronae.jwh.request.JWHRequest;
import com.kronae.jwh.util.annotation.AlphaVersion;
import com.kronae.jwh.util.api.JWHApi;
import org.json.simple.JSONObject;

@AlphaVersion @Deprecated
public class JWHLoginApi implements JWHApi {
    private JSONObject loginUsers;
    private JSONObject users;
    private JSONObject sessions;
    @Override
    public void setup(JWH web) {
        loginUsers = new JSONObject();
        users      = new JSONObject();

        web.addEventListener(new JWHEventListener() {
            @JWHEventHandler
            public void onStart(JWHEvent_ServerStartEvent event) {
                System.out.println("Start!!!");
            }
        });
    }
    public boolean loginUser(String address, String account, String password) {
        JSONObject acc = (JSONObject) users.get(account);
        if(acc != null) {
            String pw = (String) acc.get("password");
            if(pw.equals(password)) {
                loginUsers.put(address, account);
            }
        }
        return false;
    }
    public boolean checkIsLogin(String address) {
        Object login = loginUsers.get(address);
        return login != null;
    }
    public String getLoginAccount(String address) {
        Object login = loginUsers.get(address);
        return login == null ? null : (String) login;
    }
    public void logoutUser(String address) {
        loginUsers.put(address, null);
    }
}