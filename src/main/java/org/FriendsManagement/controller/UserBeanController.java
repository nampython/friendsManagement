//package org.FriendsManagement.controller;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import reactor.core.publisher.Flux;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//
//@Configuration
//public class UserBeanController {
//
//    @Bean
//    public RouterFunction<?> userRouterFunction() {
//        return route(
//                GET("/api/v1/test"),
//                request -> ok().body(Flux.just("Hello World"), String.class)
//        );
//    }
//}
