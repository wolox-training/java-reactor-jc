package com.wolox.reactortraining.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Topic {

  @Id
  private String id;
  private String description;
}
