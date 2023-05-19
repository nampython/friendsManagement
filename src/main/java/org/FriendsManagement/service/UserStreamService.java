package org.FriendsManagement.service;

import org.FriendsManagement.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserStreamService {
     Mono<User> createUser(User user);
    Flux<User> getAllUsers();
    Mono<User> findById(String userId);
    Mono<User> updateUser(String userId,  User user);
    Mono<User> deleteUser(String userId);
    Flux<User> fetchUsers(String name);
}
