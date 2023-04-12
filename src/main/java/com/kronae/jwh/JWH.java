package com.kronae.jwh;

import com.kronae.jwh.event.JWHEvent;
import com.kronae.jwh.event.JWHEventHandler;
import com.kronae.jwh.event.JWHEventListener;
import com.kronae.jwh.event.event.JWHEvent_ServerOpenEvent;
import com.kronae.jwh.request.JWHRequest;
import com.kronae.jwh.request.JWHRequestHandler;
import com.kronae.jwh.response.JWHResponse;
import com.kronae.jwh.util.api.JWHApi;
import com.sun.net.httpserver.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JWH {
    private HttpServer server = null;
    private ArrayList<JWHRequestHandler> handlers;
    public Charset charset;
    private Map<Class<? extends JWHEvent>, ArrayList<Method>> eventListener;

    public JWH(@Range(from=0, to=65536) int port) {
        try {
            this.server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        handlers = new ArrayList<>();
        eventListener = new HashMap<>();
        charset  = Charset.defaultCharset();

        server.createContext("/", exchange -> {
            for (JWHRequestHandler handler : handlers) {
                try {
                    handler.handle(JWHRequest.fromExchange(exchange), JWHResponse.fromExchange(exchange, charset));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void addHandler(JWHRequestHandler handler) {
        handlers.add(handler);
    }
    public <API extends JWHApi> API addApi(API api) {
        api.setup(this);
        return api;
    }
    public void addEventListener(JWHEventListener listener) {
        addEventListener(null, listener);
    }
    public void addEventListener(@Nullable Class<? extends JWHEvent> eventClass, @NotNull JWHEventListener listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if(!method.isAnnotationPresent(JWHEventHandler.class))
                continue;

            Class<?>[] params = method.getParameterTypes();
            if(params.length != 1)
                continue;

            if(eventClass == null || params[0] == eventClass) {
                ArrayList<Method> listeners = eventListener.getOrDefault(eventClass, new ArrayList<>());
                listeners.add(method);
                if(eventClass == null) {
                    eventListener.put((Class<? extends JWHEvent>) params[0], listeners);
                } else {
                    eventListener.put(eventClass, listeners);
                }
            }
        }
    }
    public void callEvent(JWHEvent event) {
        eventListener.forEach((eventClass, methods) -> {
            if(event.getClass() == eventClass) {
                methods.forEach(method -> {
                    Class<?> clazz = method.getDeclaringClass();

                    Object instance;
                    try {
                        instance = clazz.getConstructor().newInstance();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace(System.err);
                        System.err.println("JWH: JWH.java: ERROR: Cannot find");
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace(System.err);
                        System.err.println("JWH: JWH.java: ERROR: Cannot access to the class/method!");
                        return;
                    } catch (InvocationTargetException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        method.invoke(instance, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
    public void start() {
        server.setExecutor(null);
        server.start();
        callEvent(new JWHEvent_ServerOpenEvent(this));
    }
    public void stop(int delay) {
        server.stop(delay);
    }
}
