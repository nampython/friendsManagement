package org.FriendsManagement.respository;

import org.FriendsManagement.model.friends.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveDao extends ReactiveCrudRepository<UserEntity, Integer> {
    Mono<UserEntity> findByEmail(Mono<String> email);
}
