package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.request.BotRequest;
import com.wolox.reactortraining.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterController {

  @Autowired
  private TwitterService twitterService;

  @GetMapping("/user")
  public String getUserName() {
    return this.twitterService.getUsername();
  }
}
