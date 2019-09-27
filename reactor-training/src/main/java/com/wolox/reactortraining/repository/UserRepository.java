package com.wolox.reactortraining.repository;

import com.wolox.reactortraining.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
