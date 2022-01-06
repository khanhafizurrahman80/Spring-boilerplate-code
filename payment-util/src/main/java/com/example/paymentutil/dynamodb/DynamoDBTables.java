package com.example.paymentutil.dynamodb;

/*
 * @author Khan Hafizur Rahman
 * @since 31/12/21
 */
public class DynamoDBTables {
    private DynamoDBTables () {

    }
    public static final String TABLE_TRANSACTION_LIFECYCLE = "-payment-transactionLifeCycle";
    public static final String TABLE_CONFIGURATIONS = "-payment-configurations";

    public static final String GSI_TRANSACTION_LIFECYCLE_USER_IDENTITY_NUMBER = "transactionLifeCycle-gi-userIdentityNumber";
}
