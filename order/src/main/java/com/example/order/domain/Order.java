package com.example.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

  @JsonProperty("orderId")
  protected String id = UUID.randomUUID().toString();
  protected Customer customer = new Customer();
  protected List<OrderItem> items = new ArrayList<>();

  public void addItem(OrderItem i) {
    items.add(i);
  }

  public int getTotalSum() {
    int sum = 0;
    for (OrderItem orderItem : items) {
      sum += orderItem.getAmount();
    }
    return sum;
  }
}
