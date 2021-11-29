package com.sfg.gateway.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancedEurekaRoutes {
    @Bean
    public RouteLocator loadBalancedRouteConfig(RouteLocatorBuilder builder) {
        System.out.println("Enter");
        return builder.routes()
                .route(r -> r.path("/api/v1/beerUpc/*", "/api/v1/beer/*", "/api/v1/beer*")
                        //.filters(f -> f.rewritePath("/api/v1/beerUpc/(?<segment>/?.*,)", "/${segment}")
                        .uri("lb://beer-service")
                        .id("beer-service")
                )
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .filters(f->f.circuitBreaker(c->c.setName("inventory_circuit_breaker")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")
                        ))
                        .uri("lb://inventory-service")

                ).route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://order-service")
                        .id("beer-Order-Service"))
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://inventory-failover-service")
                        .id("inventoryFailover"))
                .build();

    }
}
