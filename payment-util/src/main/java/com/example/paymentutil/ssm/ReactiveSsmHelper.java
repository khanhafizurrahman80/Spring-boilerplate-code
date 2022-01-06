package com.example.paymentutil.ssm;

import com.example.paymentutil.AwsLocalEndPoints;
import com.example.paymentutil.ProfileConstants;
import com.example.paymentutil.exceptions.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmAsyncClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/*
 * @author Khan Hafizur Rahman
 * @since 6/1/22
 */
@Slf4j
public class ReactiveSsmHelper {
    public static final String SSM_ERROR = "SSM_ERROR";

    private String profile;

    public ReactiveSsmHelper(String profile) {
        this.profile = profile;
    }

    protected Map<String, String> getSsmValues(Map<String, String> ssmKeys) {
        var client = ssmAsyncClient();

        try {
            return Flux.fromIterable(ssmKeys.keySet())
                    .flatMap(key -> getSsmParameterValue(ssmKeys.get(key), client)
                            .map(ssmValue -> Tuples.of(key, ssmValue)))
                    .collectMap(Tuple2::getT1, Tuple2::getT2)
                    .toFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Local repository data get from redis error", e);
            Thread.currentThread().interrupt();
            System.exit(1);
        }

        return new HashMap<>();
    }
    protected String decodeBase64(String data) {
        return new String(Base64.getDecoder().decode(data));
    }

    private Mono<String> getSsmParameterValue(String key, SsmAsyncClient ssmAsyncClient) {
        var finalSsmKey = String.format(key, profile);
        log.info("Retrieving SSM Key --> {}", finalSsmKey);

        var parameterRequest = GetParameterRequest.builder()
                .name(finalSsmKey)
                .withDecryption(true)
                .build();

        var future = ssmAsyncClient.getParameter(parameterRequest);

        return Mono.fromCompletionStage(future)
                .map(getParameterResponse -> getParameterResponse.parameter().value())
                .onErrorMap(throwable -> new InternalException(HttpStatus.INTERNAL_SERVER_ERROR, SSM_ERROR, throwable));
    }

    private SsmAsyncClient ssmAsyncClient() {
        SsmAsyncClient ssmAsyncClient;

        if (ProfileConstants.PROFILE_DEV.equals(this.profile)) {
            ssmAsyncClient = SsmAsyncClient.builder()
                    .region(Region.AP_SOUTHEAST_1)
                    .endpointOverride(URI.create(AwsLocalEndPoints.SSM_ENDPOINT))
                    .build();
        } else {
            ssmAsyncClient = SsmAsyncClient.builder()
                    .region(Region.AP_SOUTHEAST_1)
                    .build();
        }

        return ssmAsyncClient;
    }
}
