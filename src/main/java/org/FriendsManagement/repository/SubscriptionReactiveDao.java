package org.FriendsManagement.repository;

import org.FriendsManagement.model.friends.Friendship;
import org.FriendsManagement.model.friends.Subscription;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface SubscriptionReactiveDao extends R2dbcRepository<Subscription, Integer> {
}
