//package org.FriendsManagement.controller;
//
//import org.FriendsManagement.model.User;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//class UserControllerTest {
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Test
//    void getAllUsers() {
//        webTestClient
//                .get()
//                .uri("/api/v1/users")
//                .exchange()
//                .expectStatus().isOk();
//    }
//}