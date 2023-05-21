package org.FriendsManagement.repository;

import org.FriendsManagement.model.friends.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReactiveDao extends R2dbcRepository<User, Integer> {
    Mono<User> findByEmail(String email);
    Mono<User> findByUserId(int userId);
}
