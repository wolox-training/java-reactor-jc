package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.facade.BotFacade;
import com.wolox.reactortraining.request.BotRequest;
import com.wolox.reactortraining.response.BotResponse;
import com.wolox.reactortraining.services.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BotController {

  @Autowired
  private BotFacade botFacade;

  @PostMapping("/bot")
  @ResponseStatus(HttpStatus.CREATED)
  public void createBot(@RequestBody BotRequest botRequest) throws Exception {
    this.botFacade.createBot(botRequest);
  }

  @GetMapping("/talk")
  public Mono<BotResponse> getBotTalk(@RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "20") String length) {
      return this.botFacade.getBotTalk(name, length);
  }
}
