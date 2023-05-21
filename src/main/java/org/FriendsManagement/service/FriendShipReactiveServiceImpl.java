package org.FriendsManagement.service;

import org.FriendsManagement.model.friends.Friendship;
import org.FriendsManagement.model.friends.User;
import org.FriendsManagement.repository.FriendshipReactiveDao;
import org.FriendsManagement.repository.UserReactiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Transactional
public class FriendShipReactiveServiceImpl implements FriendShipReactiveService {
    private final UserReactiveDao userReactiveDao;
    private final FriendshipReactiveDao friendshipReactive;

    @Autowired
    public FriendShipReactiveServiceImpl(UserReactiveDao userRepository, FriendshipReactiveDao friendshipRepositoryReactive) {
        this.userReactiveDao = userRepository;
        this.friendshipReactive = friendshipRepositoryReactive;
    }


    /**
     * @param email
     * @return
     */
    @Override
    public Flux<String> getFriendsListByEmail(String email) {
        return userReactiveDao.findByEmail(email)
                .doOnNext(user -> {
                    System.out.println(String.format("Get user by email: %s -> User: %s", email, user));
                })
                .onErrorResume(throwable -> {
                    throw new RuntimeException(String.format("Message = Error in finding user by email: %s", email));
                })
                .flux().concatMap(
                        user -> friendshipReactive.findByUserIdAndStatus(user.getUserId(), "accepted")
                )
                .doOnNext(friendship -> {
                    System.out.println(String.format("Get friends from userId: %s and status: %s -> %s", friendship.getUserId(), "accepted", friendship));
                })
                .onErrorResume(
                        throwable -> {
                            throw new RuntimeException("Message = Error in getting user by id and status");
                        }
                )
                .concatMap(
                        friendship -> userReactiveDao.findByUserId(friendship.getFriendId())
                )
                .onErrorResume(throwable -> {
                    throw new RuntimeException("Message = Error in getting users");
                })
                .map(User::getEmail);

    }


    /**
     * @param email1
     * @param email2
     * @return
     */
    @Override
    public Flux<String> getCommonFriends(String email1, String email2) {
        return getUserIdByEmail(email1)
                .flatMapMany(userId1 -> getUserIdByEmail(email2)
                        .flatMapMany(userId2 -> {
                            Flux<Friendship> friend1 = friendshipReactive.findByUserIdAndStatus(userId1, "accepted")
                                    .cache(); // Cache the result for reuse
                            return friendshipReactive.findByUserIdAndStatus(userId2, "accepted")
                                    .filter(friend2 -> Boolean.TRUE.equals(friend1.any(friend1Item -> friend1Item.getFriendId() == friend2.getFriendId()).block()))
                                    .flatMap(friend2 -> userEmailByUserId(friend2.getFriendId()));
                        })
                );
    }


    /**
     * Takes an email as a parameter and returns the userId of the
     * corresponding User object. It returns a Mono&lt;Integer&gt; that emits
     * either one Integer or no value at all. If there is no matching User object in the database, then
     * getUserIdByEmail will return an empty Mono&lt;Integer&gt;. Otherwise, it will return a Mono&lt;Integer&gt; that emits one Integer value:
     *
     * @param email Find a user by their email address
     * @return A mono&lt;integer&gt; object
    **/
    private Mono<Integer> getUserIdByEmail(String email) {
        return userReactiveDao
                .findByEmail(email)
                .map(User::getUserId)
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Take a userId as a parameter and return the email of the corresponding object.
     *
     * @param userID: userDd of a specified user
     * @return userEmail
     */

    private Mono<String> userEmailByUserId(Integer userID) {
        return userReactiveDao
                .findByUserId(userID)
                .map(User::getEmail)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Friendship> getFriendShipByUSerIdAndStatus(int userId) {
        return friendshipReactive.findByUserIdAndStatus(userId, "accepted");
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public Mono<User> getUsersByEmail(String email) {
        return null;
    }
}
