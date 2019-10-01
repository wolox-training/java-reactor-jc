package com.wolox.reactortraining.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BotRequest {

  private String userName;
  private String botName;
  private List<String> topics;
}
