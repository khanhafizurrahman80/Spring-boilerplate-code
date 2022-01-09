package com.example.externalevent.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/*
 * @author Khan Hafizur Rahman
 * @since 9/1/22
 */
@Configuration
public class WebClientConfiguration {
    private static final String MIME_TYPE_JSON = MediaType.APPLICATION_JSON_VALUE;

    @Value("${payment.core.url}")
    private String paymentCoreUrl;

    @Bean
    public WebClient paymentCoreWebClient() {
        return WebClient.builder()
                .baseUrl(paymentCoreUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MIME_TYPE_JSON)
                .build();
    }
}
