package com.wolox.reactortraining.repository;

import com.wolox.reactortraining.models.Topic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TopicRepository extends ReactiveMongoRepository<Topic, String> {

}
