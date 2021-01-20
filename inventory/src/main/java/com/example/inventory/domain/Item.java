package com.example.inventory.domain;

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
public class Item {

  private String articleId;
  private int amount;

  public Item setArticleId(String articleId) {
    this.articleId = articleId;
    return this;
  }
}
