package com.wolox.reactortraining.services;

import com.wolox.reactortraining.request.Boot;
import com.wolox.reactortraining.response.BootResponse;
import reactor.core.publisher.Mono;

public interface BotService {

  Mono<BootResponse> getBotTalk( String name, String length);
  void createBook(Boot boot);
}
