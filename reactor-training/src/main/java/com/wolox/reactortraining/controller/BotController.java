package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.facade.BotFacade;
import com.wolox.reactortraining.request.BotRequest;
import com.wolox.reactortraining.response.BotResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BotController {

  @Autowired
  private BotFacade botFacade;

  @PostMapping("/bot")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<BotRequest> createBot(@RequestBody BotRequest botRequest) throws Exception {
    return this.botFacade.createBot(botRequest);
  }

  @GetMapping("/talk")
  public Mono<BotResponse> getBotTalk(@RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "20") String length) {
      return this.botFacade.getBotTalk(name, length);
  }

  @GetMapping(path = "/conversation", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> conversationBot(@RequestParam List<String> bot) {
     return this.botFacade.getConversation(bot);
  }
}
