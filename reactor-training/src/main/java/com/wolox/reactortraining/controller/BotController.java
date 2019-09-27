package com.wolox.reactortraining.controller;

import com.wolox.reactortraining.request.Boot;
import com.wolox.reactortraining.response.BootResponse;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class BotController {

  @Value("${api.bots.uri}")
  private String urlBoot;

  @PostMapping("/feed")
  @ResponseStatus(HttpStatus.CREATED)
  public void createBook(@RequestBody Boot boot) {
    RestTemplate restTemplate = new RestTemplate();
    Boot bootResponse = restTemplate.postForObject(this.urlBoot + "feed", boot, Boot.class);
  }

  @GetMapping("/talk")
  public Mono<BootResponse> getBotTalk(@RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "20") String length) {
    WebClient webClient = WebClient.create(this.urlBoot);
    return webClient.get().uri("took?length={length}&name={name}", length, name)
        .retrieve().bodyToMono(BootResponse.class);
  }
}
