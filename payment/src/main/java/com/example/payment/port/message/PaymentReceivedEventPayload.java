package com.example.payment.port.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentReceivedEventPayload {

  private String refId;

  public PaymentReceivedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }
}
