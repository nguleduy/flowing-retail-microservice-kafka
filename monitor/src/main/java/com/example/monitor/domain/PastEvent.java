package com.example.monitor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PastEvent {

  private String transactionId;
  private String type;
  private String name;
  private String content;
  private String sender;
}
