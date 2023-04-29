package com.kronae.jwh.util.api;

import com.kronae.jwh.JWH;

public interface JWHApi {
    /**
     * Do not execute setup(JWH). Because when you add the api, It will auto setup.
     * @param web JWH WEB
     */
    void setup(JWH web);
}
