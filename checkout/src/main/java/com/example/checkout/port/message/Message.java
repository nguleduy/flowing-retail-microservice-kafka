package com.example.checkout.port.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message<T> {

  private String messageType;
  private String id = UUID.randomUUID().toString(); // unique id of this message
  private String traceId = UUID.randomUUID().toString(); // trace id, default: new unique
  private String sender = "Checkout"; // for new messages
  private Date timestamp = new Date();

  private T payload;

  public Message(String type, T payload) {
    this.messageType = type;
    this.payload = payload;
    this.traceId = UUID.randomUUID().toString();
  }
}
