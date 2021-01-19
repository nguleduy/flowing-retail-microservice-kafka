package com.example.order.application.adapter;

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
public class RetrievePaymentPayload {

  private String refId;
  private String reason;
  private int amount;

  public RetrievePaymentPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public RetrievePaymentPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }

  public RetrievePaymentPayload setAmount(int amount) {
    this.amount = amount;
    return this;
  }
}
