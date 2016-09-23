package com.theiconic.android.shop.categories.entities;

import android.support.v4.util.ArrayMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

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


  public Map<String, String> getQueryMap() {
    Map<String, String> queryMap = new ArrayMap<>();
    String query = getQuery();

    if (value == null || value.isEmpty() || query == null) {
      return queryMap;
    }

    String[] queryParams = query.split("&");

    for (String queryParam : queryParams) {
      int index = queryParam.indexOf('=');
      if (index != -1) {
        queryMap.put(queryParam.substring(0, index), queryParam.substring(index + 1, queryParam.length()));
      }
    }

    return queryMap;
  }

  @Override
  public String toString() {
    return value;
  }
}
