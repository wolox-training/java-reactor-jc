package com.wolox.reactortraining.services;

import com.wolox.reactortraining.request.Bot;
import com.wolox.reactortraining.response.BotResponse;
import reactor.core.publisher.Mono;

public interface BotService {

  Mono<BotResponse> getBotTalk( String name, String length);
  void createBot(Bot boot);
}
