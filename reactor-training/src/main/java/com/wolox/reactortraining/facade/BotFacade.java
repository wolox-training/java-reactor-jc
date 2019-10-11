package com.wolox.reactortraining.facade;

import com.wolox.reactortraining.request.Bot;
import com.wolox.reactortraining.request.BotRequest;
import com.wolox.reactortraining.response.BotResponse;
import com.wolox.reactortraining.services.BotService;
import com.wolox.reactortraining.services.TwitterService;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BotFacade {

  @Autowired
  private TwitterService twitterService;

  @Autowired
  private BotService botService;



  public Mono<BotRequest> createBot(BotRequest botRequest) throws Exception {
    Flux<String> tweets = this.twitterService.getTweetStream(botRequest);
    List<String> listTweet = tweets.collectList().block();
    Bot bot = new Bot();
    bot.setName(botRequest.getBotName());
    bot.setText(listTweet.stream().collect(Collectors.joining(" ")));
    return Mono.just(this.botService.createBot(bot));
  }

  public Mono<BotResponse> getBotTalk(String name, String length) {
    return this.botService.getBotTalk(name, length);
  }

  public Flux<String> getTwettstreaming(BotRequest botRequest) throws Exception {
    return this.twitterService.getTweetStream(botRequest);
  }

  public Flux<String> getConversation(List<String> bot) {
    Random random = new Random();
    Long timeInterval = 200L;
    int lengthTwett = 200;
    List<Flux<String>> bots = new ArrayList<>();
    for (String botElement : bot) {
      Flux<String> botlist = Flux.just(botElement)
          .repeatWhen(longFlux -> Flux.interval(Duration.ofSeconds(2))).map(nameBot ->
              this.botService.getBotTalk(nameBot, Integer.toString(random.nextInt(lengthTwett)))
          ).take(random.nextInt((7) + 3)).flatMap(botResponseMono -> botResponseMono
              .map(botResponse -> botResponse.getName() + botResponse.getResponse()));

      Flux<String> intervalFlux1 = Flux
          .interval(Duration.ofMillis(timeInterval))
          .zipWith(botlist, (i, string) -> string);
      timeInterval += 200L;
      bots.add(botlist);
    }

    Flux<String> conversationReactive = Flux.merge(Flux.fromIterable(bots));
    return Flux.interval(Duration.ofSeconds(5))
        .zipWith(conversationReactive, (tick, status) -> status).map(String::toLowerCase);
  }
}
