package com.wolox.reactortraining.services;

import com.wolox.reactortraining.bean.TwitterTemplateCreator;
import com.wolox.reactortraining.exception.TwitterNotFoundException;
import com.wolox.reactortraining.request.BotRequest;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import twitter4j.Status;

@Service
public class TwitterService {

  private final TwitterTemplateCreator twitterTemplateCreator;
  private Twitter twitter;

  public TwitterService(TwitterTemplateCreator twitterTemplateCreator) {
    this.twitterTemplateCreator = twitterTemplateCreator;
    this.twitter =  this.twitterTemplateCreator.getTwittertemplate();
  }

  public Flux<String> getTweetStream(BotRequest botRequest) throws TwitterNotFoundException {
    ConnectableFlux<Status> flux = TwitterStreamService.getTwitterStream();
    return flux.take(1000).filter(status -> {
      for (String topic:botRequest.getTopics()) {
        if (status.getText().contains(topic)){
          return true;
        }
      }
      return false;
    }).log().switchIfEmpty(Flux.error(new TwitterNotFoundException("no twetts were found with the entered topics"))).map(
        status -> status.getText());
  }

  public String getUsername() {
    TwitterProfile twitterProfile = this.twitter.userOperations().getUserProfile();
    return twitterProfile.getScreenName();
  }
}
