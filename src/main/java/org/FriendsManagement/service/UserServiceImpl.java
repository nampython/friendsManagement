package org.FriendsManagement.service;

import org.FriendsManagement.model.User;
import org.FriendsManagement.respository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<User> createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Flux<User> getAllUsers() {
        logger.info("getting all user");
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Mono<User> updateUser(String userId, User user) {
        return userRepository.findById(userId).flatMap(userDB -> {
            userDB.setName(user.getName());
            userDB.setSalary(user.getSalary());
            userDB.setAge(user.getAge());
            userDB.setDepartment(user.getDepartment());
            return userRepository.save(userDB);
        });
    }

    @Override
    public Mono<User> deleteUser(String userId) {
        return userRepository.findById(userId).flatMap(existUser -> userRepository.delete(existUser).then(Mono.just(existUser)));
    }

    @Override
    public Flux<User> fetchUsers(String name) {
//        Query query = new Query().with(
//                Sort.by(
//                        Collections.singletonList(Sort.Order.asc("age"))
//                )
//        );
//        query.addCriteria(
//                Criteria.where("name").regex(name)
//        );
//        return reactiveMongoTemplate.find(query, User.class);
        return null;
    }
}
