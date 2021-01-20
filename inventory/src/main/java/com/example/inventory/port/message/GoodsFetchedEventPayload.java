package com.example.inventory.port.message;

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
public class GoodsFetchedEventPayload {

  private String refId;
  private String pickId;

  public GoodsFetchedEventPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public GoodsFetchedEventPayload setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

}
