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
public class ShipGoodsCommandPayload {

  private String pickId;
  // assume we always use the same provider for customer orders
  private String logisticsProvider = "DHL";
  private String recipientName;
  private String recipientAddress;

  public ShipGoodsCommandPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

  public ShipGoodsCommandPayload setLogisticsProvider(String logisticsProvider) {
    this.logisticsProvider = logisticsProvider;
    return this;
  }

  public ShipGoodsCommandPayload setRecipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  public ShipGoodsCommandPayload setRecipientAddress(String recipientAddress) {
    this.recipientAddress = recipientAddress;
    return this;
  }
}
