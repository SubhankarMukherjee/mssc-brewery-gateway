package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("non-prod")
@Configuration
public class BeerOrderServiceRoute {

    @Bean
    public RouteLocator beerControllerServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes().route(r ->
                r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081")
                        .id("beer-Order-Service")
        ).build();


    }
}
