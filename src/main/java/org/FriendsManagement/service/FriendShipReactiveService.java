package org.FriendsManagement.service;

import org.FriendsManagement.model.friends.Friendship;
import org.FriendsManagement.model.friends.Subscription;
import org.FriendsManagement.model.friends.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendShipReactiveService {
    Flux<String> getFriendsListByEmail(String email);
    Mono<User> getUsersByEmail(String email);
    Flux<Friendship> getFriendShipByUSerIdAndStatus(int userId);
    Flux<String> getCommonFriends(String email1, String email2);
    Mono<Friendship> createFriendConnection(String email1, String email2);
    Mono<Subscription> subscribeToUpdates(String subscriberEmail, String targetEmail);
}
