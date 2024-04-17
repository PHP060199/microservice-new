package org.example.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes (RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/product/**")
                        .uri("lb://product-service"))

                .route(r -> r.path("/api/order/**")
                        .uri("lb://order-service"))

                .route(r -> r.path("/api/inventory/**")
                        .uri("lb://inventory-service"))

                .build();
    }
}
