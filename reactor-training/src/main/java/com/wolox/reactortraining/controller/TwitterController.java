package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.facade.BotFacade;
import com.wolox.reactortraining.request.BotRequest;
import com.wolox.reactortraining.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TwitterController {

  @Autowired
  private TwitterService twitterService;

  @Autowired
  private BotFacade botFacade;

  @GetMapping("/user")
  public String getUserName() {
    return this.twitterService.getUsername();
  }
}
