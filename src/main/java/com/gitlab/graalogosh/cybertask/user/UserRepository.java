package com.gitlab.graalogosh.cybertask.user;

import com.gitlab.graalogosh.cybertask.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 30.01.2018.
 */
public interface UserRepository extends MongoRepository<User, String> {
    public User findById(String id);
}
