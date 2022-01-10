package com.example.paymentcore.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.http.server.reactive.HttpHandler;

import java.util.HashMap;
import java.util.Map;

/*
 * @author Khan Hafizur Rahman
 * @since 10/1/22
 */
public class ReactiveWebServerFactory extends NettyReactiveWebServerFactory {

    @Value("${reactive.server.servlet.context-path}")
    private String contextPath;

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        Map<String, HttpHandler> handlerMap = new HashMap<>();
        handlerMap.put(contextPath, httpHandler);
        return super.getWebServer(new ContextPathCompositeHandler(handlerMap));
    }
}
