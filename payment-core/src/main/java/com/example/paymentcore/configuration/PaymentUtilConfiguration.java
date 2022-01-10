package com.example.paymentcore.configuration;

/*
 * @author Khan Hafizur Rahman
 * @since 10/1/22
 */

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.example.paymentutil.dynamodb.DynamoDBConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class PaymentUtilConfiguration {

    @Bean
    public DynamoDBMapper dynamoDBMapper(@Value("${spring.profiles.active:dev}") String profile) {
        var dynamoDBConfig = new DynamoDBConfig(profile);

        return dynamoDBConfig.getDynamoDBMapper();
    }


}
