package com.wolox.reactortraining.model;

import com.wolox.reactortraining.ReactorTrainingApplication;
import com.wolox.reactortraining.models.Topic;
import com.wolox.reactortraining.models.User;
import com.wolox.reactortraining.repository.TopicRepository;
import com.wolox.reactortraining.repository.UserRepository;
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
public class UserTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TopicRepository topicRepository;

  @Test
  public void saveUser() {
    User userSaved = this.userRepository.save(getUser()).block();
    Mono<User> userInDb = this.userRepository.findById(userSaved.getId());
    StepVerifier.create(userInDb)
        .assertNext(user -> {
          assertEquals("Wolox", user.getUsername());
          assertNotNull(user.getId());
        })
        .expectComplete()
        .verify();
  }

  @Test
  public void saveUserAndTopics(){
    User user = getUser();
    Topic topic1 = new Topic();
    topic1.setDescription("Reactor");
    Topic topic2 = new Topic();
    topic2.setDescription("Spring");
    Topic topicInDb1 = this.topicRepository.save(topic1).block();
    user.addTopic(topicInDb1);
    Topic topicInDb2 = this.topicRepository.save(topic2).block();
    user.addTopic(topicInDb2);
    User userSaved1 = this.userRepository.save(user).block();
    Mono<User> userInDb = this.userRepository.findById(userSaved1.getId());
    StepVerifier
        .create(userInDb)
        .assertNext(user1 -> {
          assertEquals("Wolox", user.getUsername());
          assertThat(user1.getTopics().size()).isGreaterThanOrEqualTo(2);
        })
        .expectComplete()
        .verify();
  }

  private User getUser() {
    User user = new User();
    user.setUsername("Wolox");
    return user;
  }
}
