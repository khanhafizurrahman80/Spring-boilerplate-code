package com.example.paymentutil.exceptions;

import com.example.paymentutil.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/*
 * @author Khan Hafizur Rahman
 * @since 3/1/22
 */
public class ErrorResponseBuilder {
    public static Mono<ServerResponse> createErrorResponse(String errorCode) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getErrorResponse(errorCode)));
    }

    public static Mono<ServerResponse> createErrorResponse(HttpStatus httpStatus, String errorCode) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(getErrorResponse(errorCode)));
    }

    private static ErrorResponse getErrorResponse(String errorCode) {
        return ErrorResponse.builder().errorCode(errorCode).build();
    }
}
