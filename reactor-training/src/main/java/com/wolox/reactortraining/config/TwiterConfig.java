package com.wolox.reactortraining.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource({ "file:/Users/jhovannycanas/Documents/Reactor/twitter.properties" })
public class TwiterConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
