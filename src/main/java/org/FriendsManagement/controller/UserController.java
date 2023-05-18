package org.FriendsManagement.controller;

import org.FriendsManagement.model.ResponseObjectTest;
import org.FriendsManagement.model.User;
import org.FriendsManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.FriendsManagement.controller.UserController.USER_ROUTING;

@RestController
@RequestMapping(path = USER_ROUTING)
public class UserController {
    protected static final String USER_ROUTING = "/api/v1/users";
    protected static final String GET_ALL_USERS = "";
    protected static final String SAVE_USER = "/";
    protected static final String GET_USER_BY_ID = "/{userId}";
    protected static final String UPDATE_USER_BY_ID = "/{userID}";
    protected static final String DELETE_USER_BY_ID = "/{userId}";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = GET_ALL_USERS)
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = SAVE_USER, consumes = "application/json")
    public Mono<User> saveUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping(GET_USER_BY_ID)
    public Mono<ResponseEntity<ResponseObjectTest>> findUserById(@PathVariable String userId) {
        Mono<User> userByID = userService.findById(userId);
        return userByID.flatMap(
                user -> {
                    return Mono.just(ResponseEntity.ok().body(
                            new ResponseObjectTest("ok",
                                    "Find user by id successfully",
                                    Collections.singletonList(userByID))
                    ));
                }
        );
//        return userByID.map(
//                responseEntity -> ResponseEntity.ok().body(new ResponseObject(
//                        "ok",
//                        "Find user by id successfully",
//                        userByID
//                ))
//        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(UPDATE_USER_BY_ID)
    public Mono<User> updateUserByUserId(@PathVariable String userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping(DELETE_USER_BY_ID)
    public Mono<User> deleteUserByUserId(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }
}
