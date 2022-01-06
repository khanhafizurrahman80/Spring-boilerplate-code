package com.example.paymentutil;

/*
 * @author Khan Hafizur Rahman
 * @since 31/12/21
 */
public class AwsLocalEndPoints {
    private AwsLocalEndPoints() {

    }
    public static final String DYNAMODB_ENDPOINT = "http://dynamodb.host:4566";
    public static final String SSM_ENDPOINT = "http://ssm.host:4566";
    public static final String SQS_ENDPOINT = "http://localhost:4566";
}
