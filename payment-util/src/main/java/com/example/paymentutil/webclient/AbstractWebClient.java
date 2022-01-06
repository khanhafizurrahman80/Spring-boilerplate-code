package com.example.paymentutil.webclient;

import com.example.paymentutil.exceptions.InternalException;
import com.example.paymentutil.exceptions.ServiceError;
import com.example.paymentutil.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/*
 * @author Khan Hafizur Rahman
 * @since 6/1/22
 */
@Slf4j
public class AbstractWebClient {
    private final WebClient webClient;

    public AbstractWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> Mono<T> get(String uri, Class<T> tClass) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::isError, this::getErrorResponse)
                .bodyToMono(tClass);
    }

    public <T> Mono<T> post(URI uri, HttpHeaders httpHeaders, Class<T> tClass) {
        return webClient.post()
                .uri(uri)
                .headers(httpHeadersNew -> httpHeadersNew.setAll(httpHeaders.toSingleValueMap()))
                .retrieve()
                .onStatus(HttpStatus::isError, this::getErrorResponse)
                .bodyToMono(tClass);
    }

    public <T> Mono<T> post(String uri, Object body, Class<T> tClass) {
        return webClient.post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatus::isError, this::getErrorResponse)
                .bodyToMono(tClass);
    }

    public <T> Flux<T> postFlux(String uri, Object body, Class<T> tClass) {
        return webClient.post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatus::isError, this::getErrorResponse)
                .bodyToFlux(tClass);
    }

    public <T> Mono<T> post(String uri, Object body, HttpHeaders httpHeaders, Class<T> tClass) {

        return webClient.post()
                .uri(uri)
                .headers(httpHeadersNew -> httpHeadersNew.setAll(httpHeaders.toSingleValueMap()))
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatus::isError, this::getErrorResponse)
                .bodyToMono(tClass);
    }


    private <T> Mono<T> getErrorResponse(ClientResponse clientResponse) {

        if (HttpStatus.NOT_FOUND.equals(clientResponse.statusCode())) {
            return Mono.error(new InternalException(clientResponse.statusCode(), ServiceError.SERVICE_NOT_FOUND));
        }

        return clientResponse
                .bodyToMono(ErrorResponse.class)
                .switchIfEmpty(Mono.error(new InternalException(clientResponse.statusCode(), ServiceError.UNKNOWN_ERROR)))
                .flatMap(errorResponse -> Mono.error(new InternalException(clientResponse.statusCode(), ServiceError.UNKNOWN_ERROR)));
    }
}
