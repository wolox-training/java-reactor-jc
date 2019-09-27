package com.wolox.reactortraining.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Topic {

  @Id
  private String id;
  private String description;
  @DBRef
  private List<User> users;
}
