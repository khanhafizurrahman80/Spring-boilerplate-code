package com.example.externalevent.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.http.server.reactive.HttpHandler;

import java.util.HashMap;
import java.util.Map;

/*
 * @author Khan Hafizur Rahman
 * @since 9/1/22
 */
@Configuration
public class ReactiveWebServerFactory extends NettyReactiveWebServerFactory {

    @Value("/external-event")
    private String contextPath;

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        Map<String, HttpHandler> handlerMap = new HashMap<>();
        handlerMap.put(contextPath, httpHandler);
        return super.getWebServer(new ContextPathCompositeHandler(handlerMap));
    }

}
