package com.example.inventory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PickOrder {

  private String pickId = UUID.randomUUID().toString();
  private List<Item> items;

  public PickOrder setPickId(String pickId) {
    this.pickId = pickId;
    return this;
  }

  public PickOrder setItems(List<Item> items) {
    this.items = items;
    return this;
  }
}
