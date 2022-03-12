package com.barber.servicegateway.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Routes {

    private final AuthenticationFilter filter;

    public Routes(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route("serviceTest", r -> r.path("/service-test/**")
//                        .filters(f -> f.circuitBreaker(c -> c.setName("serviceTest")
//                                       .setFallbackUri("forward:/fallback/first")))
//                        .uri("lb://SERVICE-TEST/"))

                .route("serviceWebsocket",r -> r
                        .path("/gs-rpa/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://SERVICE-WEBSOCKET/"))

                .route("serviceWebsocketAPI",r -> r
                        .path("/api/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://SERVICE-WEBSOCKET/"))

                .route("service-user", r -> r
                        .path("/users/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(c -> c
                                        .setName("cb-service-user")
                                        .setFallbackUri("forward:/fallback/fb-user")))
                        .uri("lb://SERVICE-USER"))

                .route("service-auth", r -> r
                        .path("/auth/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(c -> c
                                        .setName("cb-service-auth")
                                        .setFallbackUri("forward:/fallback/fb-auth")))
                        .uri("lb://SERVICE-AUTH"))
                .build();
    }


    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
                .build());
    }

}