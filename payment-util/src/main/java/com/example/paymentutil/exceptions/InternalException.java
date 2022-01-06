package com.example.paymentutil.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * @author Khan Hafizur Rahman
 * @since 3/1/22
 */
@Getter
public class InternalException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;

    public InternalException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.httpStatus = httpStatus;
    }

    public InternalException(HttpStatus httpStatus, String errorCode) {
        super(errorCode);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public InternalException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public InternalException(HttpStatus httpStatus, String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
