package com.wolox.reactortraining.services;

import com.wolox.reactortraining.bean.TwitterTemplateCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

  @Autowired
  private TwitterTemplateCreator twitterTemplateCreator;
  private Twitter twitter;

  public String getUserProfile()  {
    this.twitter = this.twitterTemplateCreator.getTwittertemplate();
    TwitterProfile twitterProfile = this.twitter.userOperations().getUserProfile();
    return twitterProfile.getName();
  }
}
