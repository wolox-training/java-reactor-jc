package com.wolox.reactortraining.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TwitterNotFoundException extends Exception{

  public TwitterNotFoundException(String message) {
    super(message);
  }
}
