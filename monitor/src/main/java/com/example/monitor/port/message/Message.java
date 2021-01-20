package com.example.monitor.port.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message<T> {

  private String messageType;
  private String id = UUID.randomUUID().toString(); // unique id of this message
  private String traceId = UUID.randomUUID().toString(); // trace id, default: new unique
  private String sender = null;
  private Date timestamp = new Date();

  private T payload;

  public Message(String type, T payload) {
    this.messageType = type;
    this.payload = payload;
  }

  public Message(String type, String traceId, T payload) {
    this.messageType = type;
    this.traceId = traceId;
    this.payload = payload;
  }
}
