package com.example.paymentcore.configuration;

import com.example.externalevent.ssm.SsmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

/*
 * @author Khan Hafizur Rahman
 * @since 10/1/22
 */
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SsmService ssmService;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .requestCache()
                .requestCache(NoOpServerRequestCache.getInstance())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->
                                                  Mono.fromRunnable(() -> {
                                                      swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                                  }))
                .accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> {
                            swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        }))
                .and()
                .authorizeExchange()
                .pathMatchers("/actuator/health")
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        return new MapReactiveUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
