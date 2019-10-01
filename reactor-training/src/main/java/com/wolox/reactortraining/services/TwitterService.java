package com.wolox.reactortraining.services;

import com.wolox.reactortraining.bean.TwitterTemplateCreator;
import com.wolox.reactortraining.request.BotRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

  private final TwitterTemplateCreator twitterTemplateCreator;
  private Twitter twitter;

  public TwitterService(TwitterTemplateCreator twitterTemplateCreator) {
    this.twitterTemplateCreator = twitterTemplateCreator;
    this.twitter =  this.twitterTemplateCreator.getTwittertemplate();
  }

  public String getTweet(BotRequest botRequest) throws Exception {
    TwitterProfile twitterProfile = this.twitter.userOperations().getUserProfile(botRequest.getUserName());
    if (twitterProfile == null) {
      throw new Exception("Not found user");
    }

    String tweets  = botRequest.getTopics().stream().
        map(topic -> {
              return this.twitter.searchOperations().search("#" + topic).getTweets();
            }).flatMap(Collection::stream).map(Tweet::getText).collect(Collectors.toList()).stream()
        .collect(Collectors.joining(" "));

    System.out.println(tweets);
    return tweets;
  }

  public String getUsername() {
    TwitterProfile twitterProfile = this.twitter.userOperations().getUserProfile();
    return twitterProfile.getScreenName();
  }

}
