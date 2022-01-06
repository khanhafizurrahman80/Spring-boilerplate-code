package com.example.paymentutil.exceptions;

import io.micrometer.core.instrument.config.validate.ValidationException;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/*
 * @author Khan Hafizur Rahman
 * @since 3/1/22
 */
@Slf4j
public class ReactiveExceptionHandler extends AbstractErrorWebExceptionHandler {
    public ReactiveExceptionHandler(ErrorAttributes errorAttributes,
                                    WebProperties.Resources resources,
                                    ApplicationContext applicationContext,
                                    ServerCodecConfigurer serverCodecConfigurer) {

        super(errorAttributes, resources, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    protected <T extends ServerResponse> Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        Throwable throwable = getError(request);

        return processThrowable(throwable);
    }

    private Mono<ServerResponse> processThrowable(Throwable throwable) {
        Throwable rootCause = ExceptionUtils.getRootCause(throwable);

        if (throwable instanceof InternalException) {
            var interServiceException = (InternalException) throwable;

            return ErrorResponseBuilder.createErrorResponse(interServiceException.getHttpStatus(), interServiceException.getErrorCode());
        }else if (throwable instanceof ValidationException) {
            log.error("Request validation exception.", throwable);
            var internalCode = ServiceError.INVALID_REQUEST;

            return ErrorResponseBuilder.createErrorResponse(HttpStatus.BAD_REQUEST, internalCode);
        }else if (rootCause instanceof ConnectException || rootCause instanceof SocketTimeoutException
                || rootCause instanceof ConnectTimeoutException || rootCause instanceof SSLHandshakeException) {
            return ErrorResponseBuilder.createErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, ServiceError.SERVICE_UNAVAILABLE);
        }else if (throwable instanceof WebExchangeBindException
               || throwable instanceof ServerWebInputException) {
            log.error("Request validation exception.", throwable);
            return ErrorResponseBuilder.createErrorResponse(HttpStatus.BAD_REQUEST, ServiceError.INVALID_REQUEST);
        }else {
            var internalCode = ServiceError.UNKNOWN_ERROR;
            if (!StringUtils.isEmpty(throwable.getMessage())) {
                internalCode = throwable.getMessage();
            }
            log.error("Unknown exception.", throwable);

            return ErrorResponseBuilder.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, internalCode);
        }

    }
}
