package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

  //  @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //---------la configuration d'une maniere statique : losrque en sait les noms des micro-services -------
                .route("r1", r -> r.path("/customers/**")
                        .uri("lb://CUSTOMER-SERVICE"))
                .route("r2", r -> r.path("/products/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .build();
    }


    // cette 2eme methode c'est pour une configuration dynamic
    // ce code : a chaque fois tu recoie une requette , dans l url de cette requette il y a  le nom du micro-service
    @Bean
    DiscoveryClientRouteDefinitionLocator definitionLocator(ReactiveDiscoveryClient rdc , DiscoveryLocatorProperties properties){
        return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
    }
}
