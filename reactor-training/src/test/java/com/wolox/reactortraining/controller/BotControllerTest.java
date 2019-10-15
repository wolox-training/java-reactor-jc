package com.wolox.reactortraining.controller;

import static org.mockito.Mockito.when;
import com.wolox.reactortraining.facade.BotFacade;
import com.wolox.reactortraining.request.BotRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(BotController.class)
public class BotControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private BotFacade botFacade;

  @Test
  public void createBotTest() throws Exception {
    BotRequest botRequest = getBotRequest();
    Mono<BotRequest> botRequestMono = Mono.just(botRequest);
    when(botFacade.createBot(botRequest)).thenReturn(botRequestMono);

    webTestClient.post().uri("/bot")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(botRequest), BotRequest.class)
        .exchange()
        .expectStatus().isCreated();
  }

  @Test
  public void conversationBotTest() {
    List<String> botconservationExpected = Arrays.asList(new String("bot1conversation"),
        new String("bot2conversation"), new String("bot3conversation"));
    List<String> bots = new ArrayList<>();
    bots.add("bot1");
    bots.add("bot2");
    bots.add("bot3");
    Flux<String> conversations = getConversation(bots);

    when(botFacade.getConversation(bots)).thenReturn(conversations);

    webTestClient.get().uri(uriBuilder -> uriBuilder.path("/conversation")
        .queryParam("bot", "bot1,bot2,bot3").build())
        .exchange().expectBodyList(String.class)
        .isEqualTo(botconservationExpected);
  }

  private BotRequest getBotRequest() {
    BotRequest botRequest = new BotRequest();
    botRequest.setBotName("wolox");
    botRequest.setUserName("Cristia65904412");
    List<String> topics = new ArrayList<>();
    topics.add("una");
    botRequest.setTopics(topics);
    return botRequest;
  }

  public Flux<String> getConversation(List<String> bot) {
    return Flux.fromIterable(bot).map(s -> s + "conversation").map(String::toLowerCase);
  }
}
