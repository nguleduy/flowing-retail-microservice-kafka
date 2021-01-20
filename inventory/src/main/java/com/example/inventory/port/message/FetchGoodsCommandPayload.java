package com.example.inventory.port.message;

import com.example.inventory.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FetchGoodsCommandPayload {

  private String refId;
  private String reason = "CustomerOrder";
  private List<Item> items = new ArrayList<>();

  public FetchGoodsCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public FetchGoodsCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }

  public FetchGoodsCommandPayload setItems(List<Item> items) {
    this.items = items;
    return this;
  }
}
