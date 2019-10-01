package com.wolox.reactortraining.facade;

import com.wolox.reactortraining.request.Bot;
import com.wolox.reactortraining.request.BotRequest;
import com.wolox.reactortraining.response.BotResponse;
import com.wolox.reactortraining.services.BotService;
import com.wolox.reactortraining.services.TwitterService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BotFacade {

  @Autowired
  private TwitterService twitterService;

  @Autowired
  private BotService botService;

  public void createBot(BotRequest botRequest) throws Exception {
    String tweets = this.twitterService.getTweet(botRequest);
    Bot bot = new Bot();
    bot.setName(botRequest.getBotName());
    bot.setText(tweets);
    this.botService.createBot(bot);
  }

  public Mono<BotResponse> getBotTalk(String name, String length) {
    return this.botService.getBotTalk(name, length);
  }
}
