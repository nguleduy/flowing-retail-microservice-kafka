package com.example.payment.port.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrievePaymentCommandPayload {

  private String refId;
  private String reason;
  private int amount;

  public RetrievePaymentCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public RetrievePaymentCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }

  public RetrievePaymentCommandPayload setAmount(int amount) {
    this.amount = amount;
    return this;
  }
}
