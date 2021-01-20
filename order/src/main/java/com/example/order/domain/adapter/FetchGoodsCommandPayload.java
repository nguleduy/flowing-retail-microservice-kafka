package com.example.order.domain.adapter;

import com.example.order.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FetchGoodsCommandPayload {

  private String refId;
  private String reason = "CustomerOrder";
  private List<OrderItem> items = new ArrayList<>();

  public FetchGoodsCommandPayload setRefId(String refId) {
    this.refId = refId;
    return this;
  }

  public FetchGoodsCommandPayload setReason(String reason) {
    this.reason = reason;
    return this;
  }

  public FetchGoodsCommandPayload setItems(List<OrderItem> items) {
    this.items = items;
    return this;
  }
}
