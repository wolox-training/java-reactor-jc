package com.wolox.reactortraining.model;

import com.wolox.reactortraining.ReactorTrainingApplication;
import com.wolox.reactortraining.models.Topic;
import com.wolox.reactortraining.repository.TopicRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReactorTrainingApplication.class)
public class TopicTest {

  @Autowired
  private TopicRepository topicRepository;

  @Test
  public void saveTopic () {
    Topic topicSaved = this.topicRepository.save(getTopic()).block();
    Mono<Topic> topicInDb = this.topicRepository.findById(topicSaved.getId());
    StepVerifier
        .create(topicInDb)
        .assertNext(topic -> {
          assertEquals("Trump", topic.getDescription());
          assertEquals(topicSaved.getId(), topic.getId());
        })
        .expectComplete()
        .verify();
  }

  private Topic getTopic () {
    Topic topic = new Topic();
    topic.setDescription("Trump");
    return topic;
  }
}
