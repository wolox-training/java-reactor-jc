package com.wolox.reactortraining.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class User {

  @Id
  private String id;
  private String username;
  @DBRef
  private List<Topic> topics = new ArrayList<>();

  public void addTopic(Topic topic) {
    this.topics.add(topic);
  }
}
