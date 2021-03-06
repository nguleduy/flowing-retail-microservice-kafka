package com.example.shipping.port.message;

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
public class ShipGoodsCommandPayload {

  private String refId;
  private String pickId;
  private String logisticsProvider;
  private String recipientName;
  private String recipientAddress;

  public ShipGoodsCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

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
