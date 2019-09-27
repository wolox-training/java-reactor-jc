package com.wolox.reactortraining.services.impl;

import com.wolox.reactortraining.request.Bot;
import com.wolox.reactortraining.response.BotResponse;
import com.wolox.reactortraining.services.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BotServiceImpl implements BotService {

  @Value("${api.bots.uri}")
  private String urlBoot;

  private final WebClient webClient;

  @Autowired
  private RestTemplate restTemplate;

  public BotServiceImpl() {
    this.webClient = WebClient.create();
  }

  @Override
  public Mono<BotResponse> getBotTalk(String name, String length) {
    return webClient.get().uri( this.urlBoot +"took?length={length}&name={name}", length, name)
        .retrieve().bodyToMono(BotResponse.class);
  }

  @Override
  public void createBook(Bot bot) {
    restTemplate.postForObject(this.urlBoot + "feed", bot, Bot.class);
  }
}
