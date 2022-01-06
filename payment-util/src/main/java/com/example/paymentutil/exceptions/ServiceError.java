package com.example.paymentutil.exceptions;

/*
 * @author Khan Hafizur Rahman
 * @since 5/1/22
 */
public class ServiceError {
    private ServiceError() {

    }
    public static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";
}
