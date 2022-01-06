package com.example.paymentutil.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.example.paymentutil.AwsLocalEndPoints;
import com.example.paymentutil.ProfileConstants;

/*
 * @author Khan Hafizur Rahman
 * @since 31/12/21
 */
public class DynamoDBConfig {

    private String profile;

    public DynamoDBConfig(String profile) {
        this.profile = profile;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        if (ProfileConstants.PROFILE_DEV.equals(profile)) {
            client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(AwsLocalEndPoints.DYNAMODB_ENDPOINT, Regions.AP_SOUTHEAST_1.getName()))
                    .build();
        }

        DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNamePrefix(profile))
                .build();

        return new DynamoDBMapper(client, mapperConfig);
    }
}
