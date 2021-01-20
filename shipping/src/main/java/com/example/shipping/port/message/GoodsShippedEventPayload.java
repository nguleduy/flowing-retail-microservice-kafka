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
public class GoodsShippedEventPayload {

  private String refId;
  private String shipmentId;

  public GoodsShippedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public GoodsShippedEventPayload setShipmentId(String shipmentId) {
    this.shipmentId = shipmentId;
    return this;
  }
}
