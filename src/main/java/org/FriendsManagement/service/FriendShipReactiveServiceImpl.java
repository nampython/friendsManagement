package org.FriendsManagement.service;

import org.FriendsManagement.model.friends.Friendship;
import org.FriendsManagement.model.friends.Subscription;
import org.FriendsManagement.model.friends.User;
import org.FriendsManagement.repository.FriendshipReactiveDao;
import org.FriendsManagement.repository.SubscriptionReactiveDao;
import org.FriendsManagement.repository.UserReactiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;


@Service
@Transactional
public class FriendShipReactiveServiceImpl implements FriendShipReactiveService {
    private final UserReactiveDao userReactiveDao;
    private final FriendshipReactiveDao friendshipReactive;
    private final SubscriptionReactiveDao subscriptionReactiveDao;
    private final DatabaseClient r2dbcDatabaseClient;

    @Autowired
    public FriendShipReactiveServiceImpl(UserReactiveDao userRepository, FriendshipReactiveDao friendshipRepositoryReactive, SubscriptionReactiveDao subscriptionReactiveDao, DatabaseClient r2dbcDatabaseClient) {
        this.userReactiveDao = userRepository;
        this.friendshipReactive = friendshipRepositoryReactive;
        this.subscriptionReactiveDao = subscriptionReactiveDao;
        this.r2dbcDatabaseClient = r2dbcDatabaseClient;
    }


    /**
     * @param email
     * @return
     */
    @Override
    public Flux<String> getFriendsListByEmail(String email) {
        return userReactiveDao.findByEmail(email)
                .doOnNext(
                        user -> {
                            System.out.println(String.format("Get user by email: %s -> User: %s", email, user)
                            );
                        })
                .onErrorResume(
                        throwable -> {
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
     * @param email
     * @return
     */
    @Override
    public Mono<User> getUsersByEmail(String email) {
        return null;
    }


    /**
     * Establishes a friend connection between two email addresses.
     *
     * @param email1: the first email wants to make the friend connection
     * @param email2: the second email wants to make the friend connection
     * @return A mono&lt;friendship&gt; object
     */
//    @Override
//    public Mono<Friendship> createFriendConnection(String email1, String email2) {
//        return userReactiveDao.findByEmail(email1)
//                .zipWith(userReactiveDao.findByEmail(email2))
//                .flatMap(tuple -> {
//                    int userId1 = tuple.getT1().getUserId();
//                    int userId2 = tuple.getT2().getUserId();
//
//                    return friendshipReactive.findByUserIdAndFriendId(userId1, userId2)
//                            .flatMap(existingFriendship -> Mono.just(existingFriendship))
//                            .switchIfEmpty(
//                                    Mono.defer(() -> {
//                                        Friendship friendship = new Friendship();
//                                        friendship.setUserId(userId1);
//                                        friendship.setFriendId(userId2);
//                                        friendship.setStatus("accepted");
//                                        return friendshipReactive.save(friendship);
//                                    })
//                            );
//                });
//    }
    @Override
    public Mono<Friendship> createFriendConnection(String email1, String email2) {
//        ConnectionFactoryOptions options = ConnectionFactoryOptions.parse("r2dbc:mysql://localhost:3306/friendsmanagement")
//                .mutate()
//                .option(ConnectionFactoryOptions.USER, "root")
//                .option(ConnectionFactoryOptions.PASSWORD, "Now123DQN")
//                .option(INITIAL_SIZE, 10)
//                .option(MAX_SIZE, 20)
//                .build();
//
//        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
//        DatabaseClient databaseClient = DatabaseClient.builder()
//                .connectionFactory(connectionFactory)
//                .namedParameters(true)
//                .build();

        Mono<User> user1 = r2dbcDatabaseClient.sql("SELECT * FROM User WHERE email = :email1")
                .bind("email1", email1)
                .map((row, metadata) -> {
                    User user = new User();
                    user.setUserId(row.get("user_id", Integer.class));
                    user.setEmail(row.get("email", String.class));
                    return user;
                }).one();

        Mono<User> user2 = r2dbcDatabaseClient.sql("SELECT * FROM User WHERE email = :email2")
                .bind("email2", email1)
                .map((row, metadata) -> {
                    User user = new User();
                    user.setUserId(row.get("user_id", Integer.class));
                    user.setEmail(row.get("email", String.class));
                    return user;
                }).one();


        return
                user1.zipWith(user2)
//        userReactiveDao.findByEmail(email1)
//                .zipWith(userReactiveDao.findByEmail(email2))
                        .flatMap(tuple -> {
                            int userId1 = tuple.getT1().getUserId();
                            int userId2 = tuple.getT2().getUserId();

                            Mono<Friendship> selectQuery = r2dbcDatabaseClient.sql("SELECT * FROM Friendship WHERE (user_id = :userId1 AND friend_id = :userId2) OR (user_id = :userId2 AND friend_id = :userId1)")
                                    .bind("userId1", userId1)
                                    .bind("userId2", userId2)
                                    .map((row, metadata) -> {
                                        Friendship friendship = new Friendship();
                                        friendship.setUserId(row.get("user_id", Integer.class));
                                        friendship.setFriendId(row.get("friend_id", Integer.class));
                                        friendship.setStatus(row.get("status", String.class));
                                        return friendship;
                                    })
                                    .one();

                            return selectQuery
                                    .flatMap(existingFriendship -> Mono.just(existingFriendship))
                                    .switchIfEmpty(
                                            Mono.defer(() -> {
                                                Friendship friendship = new Friendship();
                                                friendship.setUserId(userId1);
                                                friendship.setFriendId(userId2);
                                                friendship.setStatus("accepted");
                                                return r2dbcDatabaseClient.sql("INSERT INTO Friendship (user_id, friend_id, status) VALUES (:userId1, :userId2, :status)")
                                                        .bind("userId1", userId1)
                                                        .bind("userId2", userId2)
                                                        .bind("status", friendship.getStatus())
                                                        .fetch()
                                                        .rowsUpdated()
                                                        .then(Mono.just(friendship));
                                            })
                                    );
                        });
    }


    /**
     * Takes in two strings, subscriberEmail and targetEmail.
     * It then uses the userReactiveDao to find a User with the email address of subscriberEmail.
     * It does the same for targetEmail, using that to find a second User object.
     * The function then zips these two Mono&lt;User&gt; objects together into one Mono&lt;Tuple2&gt;. This is done so that we can access both users at once when creating our Subscription object.
     * We create this Subscription object by setting its subscriberId field equal to the userId of our first User (the one who will be subscribing), and
     *
     * @param subscriberEmail subscriberEmail Find the user in the database
     * @param targetEmail     targetEmail Find the user in the database
     * @return A mono&lt;subscription&gt; object
     */
    @Override
    public Mono<Subscription> subscribeToUpdates(String subscriberEmail, String targetEmail) {
        Mono<User> subscriberMono = userReactiveDao.findByEmail(subscriberEmail);
        Mono<User> targetMono = userReactiveDao.findByEmail(targetEmail);

//        return subscriberMono.zipWith(targetMono)
//                .flatMap(tuple -> {
//                    User subscriber = tuple.getT1();
//                    User target = tuple.getT2();
//
//                    Subscription subscription = new Subscription();
//                    subscription.setSubscriberId(subscriber.getUserId());
//                    subscription.setTargetId(target.getUserId());
//
//                    return subscriptionReactiveDao.save(subscription);
//                });
        return Mono.from(
                subscriberMono
                        .flux()
                        .concatMap(subscriber -> {
                            return targetMono.publishOn(Schedulers.boundedElastic()).map( target -> {
                                Subscription subscription = new Subscription();
                                subscription.setSubscriberId(subscriber.getUserId());
                                subscription.setTargetId(target.getUserId());
                                subscriptionReactiveDao.save(subscription).subscribe();
                                return subscription;
                            });
                        })
        );
    }
}
