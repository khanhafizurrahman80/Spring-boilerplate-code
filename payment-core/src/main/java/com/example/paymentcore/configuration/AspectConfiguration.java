package com.example.paymentcore.configuration;

/*
 * @author Khan Hafizur Rahman
 * @since 10/1/22
 */

import com.example.paymentutil.logger.ReactiveLoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Bean
    public ReactiveLoggerAspect reactiveLoggerAspect() {
        return new ReactiveLoggerAspect();
    }
}
