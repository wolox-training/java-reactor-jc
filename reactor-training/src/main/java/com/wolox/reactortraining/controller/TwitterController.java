package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterController {

  @Autowired
  private TwitterService twitterService;

  @GetMapping("/user")
  public String getUsername(){
    return this.twitterService.getUserProfile();
  }
}
