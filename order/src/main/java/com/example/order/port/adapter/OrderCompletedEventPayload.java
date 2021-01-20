package com.example.order.port.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCompletedEventPayload {

  private String orderId;

  public OrderCompletedEventPayload setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }
}
