package org.FriendsManagement.respository;

import org.FriendsManagement.model.friends.FriendshipEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface FriendshipDaoReactive extends ReactiveCrudRepository<FriendshipEntity, Integer> {
    Flux<FriendshipEntity> findByUserIdAndStatus(int userId, String status);
}
