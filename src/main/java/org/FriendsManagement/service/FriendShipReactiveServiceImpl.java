package org.FriendsManagement.service;

import org.FriendsManagement.model.friends.UserEntity;
import org.FriendsManagement.repository.FriendshipReactiveDao;
import org.FriendsManagement.repository.UserReactiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FriendShipReactiveServiceImpl implements FriendShipReactiveService {
    private final UserReactiveDao userReactiveDao;
    private final FriendshipReactiveDao friendshipReactive;

    @Autowired
    public FriendShipReactiveServiceImpl(UserReactiveDao userRepository, FriendshipReactiveDao friendshipRepositoryReactive) {
        this.userReactiveDao = userRepository;
        this.friendshipReactive = friendshipRepositoryReactive;
    }


    @Override
    public Flux<String> getFriendsListByEmail(Mono<String> email) {
        return userReactiveDao.findByEmail(email)
                .flatMapMany(user -> friendshipReactive.findByUserIdAndStatus(user.getUserId(), "accepted"))
                .flatMap(friendship -> userReactiveDao.findById(friendship.getFriendId()))
                .map(UserEntity::getEmail);
    }
}
