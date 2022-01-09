package com.example.externalevent.ssm;

import com.example.paymentutil.ssm.ReactiveSsmHelper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * @author Khan Hafizur Rahman
 * @since 9/1/22
 */
@Slf4j
@Service
public class SsmService extends ReactiveSsmHelper {
    private Map<String, String> ssmValues;

    public SsmService(@Value("${spring.profiles.active:dev}") String profile) {
        super(profile);
        Map<String, String> ssmKeys = new HashMap<>();
        Arrays.stream(SsmKeys.values())
                .forEach(ssmKey -> ssmKeys.put(ssmKey.name(), ssmKey.getKey()));

        ssmValues = getSsmValues(ssmKeys);
    }

    public String getSsmValue(String key) {
        return this.ssmValues.get(key);
    }
}
