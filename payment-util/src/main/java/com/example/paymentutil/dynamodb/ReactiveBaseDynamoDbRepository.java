package com.example.paymentutil.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.paymentutil.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/*
 * @author Khan Hafizur Rahman
 * @since 31/12/21
 */
@Slf4j
public class ReactiveBaseDynamoDbRepository {
    public static final String DYNAMO_ERROR = "DYNAMO_ERROR";

    private final DynamoDBMapper dynamoDBMapper;

    protected ReactiveBaseDynamoDbRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public <T> Mono<Boolean> save(T model) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> dynamoDBMapper.save(model));

        return Mono.fromCompletionStage(future)
                .then(Mono.just(true))
                .onErrorMap(throwable -> new InternalException(HttpStatus.INTERNAL_SERVER_ERROR, DYNAMO_ERROR, throwable));
    }

    public <T> Mono<T> getData(Object hashkey, Object rangeKey, Class<T> repositoryType) {
        var future = CompletableFuture.supplyAsync(() -> dynamoDBMapper.load(repositoryType, hashkey, rangeKey));

        return Mono.fromCompletionStage(future)
                .onErrorMap(throwable -> new InternalException(HttpStatus.INTERNAL_SERVER_ERROR, DYNAMO_ERROR, throwable));
    }
}
