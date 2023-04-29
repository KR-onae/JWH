package com.kronae.jwh.util;

import java.lang.reflect.Method;

public class JWHEventHelper {
    private final Method method;
    private final Object instance;
    public JWHEventHelper(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }
    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }
}
