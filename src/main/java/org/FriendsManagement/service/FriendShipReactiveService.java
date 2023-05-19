package org.FriendsManagement.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendShipReactiveService {
    Flux<String> getFriendsListByEmail(Mono<String> email);
}
