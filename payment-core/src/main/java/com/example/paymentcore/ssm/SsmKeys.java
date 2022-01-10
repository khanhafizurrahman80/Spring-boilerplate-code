package com.example.paymentcore.ssm;

/*
 * @author Md. Shahinur Rahman
 * @since 9/27/21
 */
public enum SsmKeys {
    PAYMENT_USERNAME("/%s/remittance/api/userName");

    private final String key;

    SsmKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
