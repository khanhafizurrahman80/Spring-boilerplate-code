package com.example.externalevent.ssm;

/*
 * @author Khan Hafizur Rahman
 * @since 9/1/22
 */
public enum SsmKeys {
    SSL_EXTERNAL_USERNAME("/%s/remittance/external/userName");
    private final String key;

    SsmKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
