package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.request.Bot;
import com.wolox.reactortraining.response.BotResponse;
import com.wolox.reactortraining.services.BotService;
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

  private final BotService botService;

  public BotController(BotService botService) {
    this.botService = botService;
  }


  @PostMapping("/feed")
  @ResponseStatus(HttpStatus.CREATED)
  public void createBook(@RequestBody Bot bot) {
    this.botService.createBook(bot);
  }

  @GetMapping("/talk")
  public Mono<BotResponse> getBotTalk(@RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "20") String length) {
      return this.botService.getBotTalk(name, length);
  }
}
