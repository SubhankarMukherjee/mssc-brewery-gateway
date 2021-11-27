package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("non-prod")
@Configuration
public class BeerServiceAndInventoryRoute {

    @Bean
    public RouteLocator beerServiceRouteConfig(RouteLocatorBuilder builder) {
        System.out.println("Enter");
        return builder.routes()
                .route(r -> r.path("/api/v1/beerUpc/*","/api/v1/beer/*","/api/v1/beer*")
                        //.filters(f -> f.rewritePath("/api/v1/beerUpc/(?<segment>/?.*,)", "/${segment}")
                        .uri("http://localhost:8080")
                        .id("beer-service")
                        )
                .route(r->r.path("/api/v1/beer/*/inventory")
                        .uri("http://localhost:8082"))
                .build();

    }
}
