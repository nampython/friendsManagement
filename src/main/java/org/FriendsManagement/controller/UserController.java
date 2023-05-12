package org.FriendsManagement.controller;

import org.FriendsManagement.model.User;
import org.FriendsManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/api/v1/users")
    public Flux<User> findAllUsers() {
        return userService.getAllUsers();
    }
}
