package com.example.order.domain.adapter;

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
