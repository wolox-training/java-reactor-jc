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



  public void createBot(BotRequest botRequest) throws Exception {
    Flux<String> tweets = this.twitterService.getTweetStream(botRequest);
    List<String> listTweet = tweets.collectList().block();
    Bot bot = new Bot();
    bot.setName(botRequest.getBotName());
    bot.setText(listTweet.stream().collect(Collectors.joining(" ")));
    this.botService.createBot(bot);
  }

  public Mono<BotResponse> getBotTalk(String name, String length) {
    return this.botService.getBotTalk(name, length);
  }

  public Flux<String> getTwettstreaming(BotRequest botRequest) throws Exception {
    return this.twitterService.getTweetStream(botRequest);
  }

  public Flux<String> getConversation(List<String> bot) {
    Random random = new Random();
   List<Flux<String>> bots =  new ArrayList<>();
    for (String botElement: bot) {
      Flux<String> botlist = Flux.just(botElement).repeatWhen(longFlux -> Flux.interval(Duration.ofSeconds(2))).map( s ->
          this.botService.getBotTalk(s, "200")
      ).take(random.nextInt((7) + 3)).flatMap(botResponseMono -> botResponseMono.map(botResponse -> botResponse.getName() + botResponse.getResponse()));
      bots.add(botlist);
    }

    Flux<String> botUno = Flux.just(bot.get(0)).repeatWhen(longFlux -> Flux.interval(Duration.ofSeconds(2))).map( s ->
        this.botService.getBotTalk(s, "200")
    ).take(random.nextInt((7) + 3)).flatMap(botResponseMono -> botResponseMono.map(botResponse -> botResponse.getName() + botResponse.getResponse()));

    Flux<String> botDos = Flux.just(bot.get(1)).repeatWhen(longFlux -> Flux.interval(Duration.ofSeconds(2))).map( s ->
        this.botService.getBotTalk(s, "200")
    ).take(random.nextInt((7) + 3)).flatMap(botResponseMono -> botResponseMono.map(botResponse -> botResponse.getName() + botResponse.getResponse()));
    Flux<String> conversation = Flux.merge(botUno.delayElements(Duration.ofMillis(400L)), botDos.delayElements(Duration.ofMillis(200L)));
    return Flux.interval(Duration.ofSeconds(1))
        .zipWith(conversation, (tick, status) -> status).map(String::toLowerCase);
  }
}
