package org.FriendsManagement.repository;

import org.FriendsManagement.model.friends.FriendshipEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface FriendshipReactiveDao extends R2dbcRepository<FriendshipEntity, Integer> {
    Flux<FriendshipEntity> findByUserIdAndStatus(int userId, String status);
}
