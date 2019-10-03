package com.wolox.reactortraining.services;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterStreamService {

  @Autowired
  private Environment env;

  private static ConnectableFlux twitterStream;

  public static synchronized ConnectableFlux getTwitterStream() {
    if (twitterStream == null) {
        initTwitterStream();
    }
    return twitterStream;
  }

  private static void initTwitterStream() {
    Flux<Status> stream = Flux.create(emitter -> {
      StatusListener statusListener = new StatusListener() {
        @Override
        public void onStatus(Status status) {
          emitter.next(status);
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

        }

        @Override
        public void onScrubGeo(long userId, long upToStatusId) {

        }

        @Override
        public void onStallWarning(StallWarning warning) {

        }

        @Override
        public void onException(Exception ex) {
          emitter.error(ex);
        }
      };

      ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
      configurationBuilder.setDebugEnabled(true)
          .setOAuthConsumerKey("GRpIu6q3DAwXgxo0OqfaDs15q")
          .setOAuthConsumerSecret("4jnEwcbG75e7k3XJ0LpQr5qkd2hpZu44yu1EDRNeUqfXMeKhTH")
          .setOAuthAccessToken("1137030251106770944-QdaJNrizA1aYpv5KpDpcAEdQkOQSrZ")
          .setOAuthAccessTokenSecret("xCacCbRJAtxOm5BtikbE7cGgRUB6zHihOmv5heE85Zpg1");

      TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
      twitterStream.addListener(statusListener);
      twitterStream.sample();

    } );
    twitterStream = stream.publish();
    twitterStream.connect();
  }
}
