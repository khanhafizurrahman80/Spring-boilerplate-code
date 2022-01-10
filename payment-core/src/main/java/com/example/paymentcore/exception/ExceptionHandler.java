package com.example.paymentcore.exception;

import com.example.paymentutil.exceptions.ReactiveExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/*
 * @author Khan Hafizur Rahman
 * @since 10/1/22
 */
@Slf4j
@Component
@Order(-2)
public class ExceptionHandler extends ReactiveExceptionHandler {
    public ExceptionHandler(ErrorAttributes errorAttributes,
                            ApplicationContext applicationContext,
                            ServerCodecConfigurer serverCodecConfigurer) {

        super(errorAttributes, new ResourceProperties(), applicationContext, serverCodecConfigurer);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }
}
