package com.example.paymentcore.configuration;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


/*
 * @author Khan Hafizur Rahman
 * @since 10/1/22
 */
@Configuration
public class WebClientConfiguration {
    private static final String MIME_TYPE_JSON = MediaType.APPLICATION_JSON_VALUE;

    @Bean
    public WebClient partnerWebClient(@Value("${}") String baseUrl) throws Exception {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/xml;charset=ISO-8859-1")
                .defaultHeader(HttpHeaders.ACCEPT, "application/xml;charset=ISO-8859-1")
                .clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
}
