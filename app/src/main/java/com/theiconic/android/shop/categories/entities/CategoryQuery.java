package com.theiconic.android.shop.categories.entities;

import java.net.URI;
import java.net.URISyntaxException;

import lombok.Data;

@Data
public class CategoryQuery {
  String value;

  public CategoryQuery(String value) {
    this.value = value;
  }

  public String getQuery() {
    try {
      URI uri = new URI(value);
      return uri.getQuery();
    } catch (URISyntaxException e) {
      return null;
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
