package org.FriendsManagement.service;

import org.FriendsManagement.model.User;
import org.FriendsManagement.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<User> createUser(User user) {
        return null;
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String userId) {
        return null;
    }

    @Override
    public Mono<User> updateUser(String userId, User user) {
        return null;
    }

    @Override
    public Mono<User> deleteUser(String userId) {
        return null;
    }

    @Override
    public Flux<User> fetchUsers(String name) {
        return null;
    }
}
