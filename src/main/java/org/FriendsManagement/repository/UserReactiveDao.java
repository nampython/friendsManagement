package org.FriendsManagement.repository;

import org.FriendsManagement.model.friends.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveDao extends R2dbcRepository<UserEntity, Integer> {
    Mono<UserEntity> findByEmail(Mono<String> email);
}
