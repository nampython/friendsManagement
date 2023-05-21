package org.FriendsManagement.controller;

import org.FriendsManagement.model.friends.Friendship;
import org.FriendsManagement.model.friends.User;
import org.FriendsManagement.service.FriendShipReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Flux<String> getFriendList(@RequestParam String email) {
        return friendShipReactiveService.getFriendsListByEmail(email);
    }

    @PostMapping(value = "/users")
    public Mono<User> getUserByEmail(@RequestParam String email) {
        return friendShipReactiveService.getUsersByEmail(email);
    }

    @PostMapping(value = "/users/friend")
    public Flux<Friendship> getFriendShipByUSerIdAndStatus(@RequestParam int userId) {
        return friendShipReactiveService.getFriendShipByUSerIdAndStatus(userId);
    }
    @GetMapping(value = "/users/common")
    public Flux<String> getCommonFriends(@RequestParam String email1, @RequestParam String email2) {
        return friendShipReactiveService.getCommonFriends(email1, email2);
    }

}
