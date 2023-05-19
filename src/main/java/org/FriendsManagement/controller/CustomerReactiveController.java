package org.FriendsManagement.controller;

import org.FriendsManagement.service.FriendShipReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerReactiveController {

    private final FriendShipReactiveService friendShipReactiveService;

    @Autowired
    public CustomerReactiveController(FriendShipReactiveService friendShipReactiveService) {
        this.friendShipReactiveService = friendShipReactiveService;
    }

    @PostMapping(value = "/users/friends")
    public Flux<String> getFriendList(@RequestParam Mono<String> email) {
        return friendShipReactiveService.getFriendsListByEmail(email);
    }
}
