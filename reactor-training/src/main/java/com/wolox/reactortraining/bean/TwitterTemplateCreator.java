package com.wolox.reactortraining.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwitterTemplateCreator {

  @Autowired
  private Environment env;

  public Twitter getTwittertemplate(){

    String consumerKey = env.getProperty("spring.social.twitter.appId");
    String consumerSecret =  env.getProperty("spring.social.twitter.appSecret");
    String accesToken = env.getProperty("twitter.access.token");
    String accesTokenSecret = env.getProperty("twitter.access.token.secret");
    return new TwitterTemplate(consumerKey, consumerSecret, accesToken, accesTokenSecret);
  }
}
