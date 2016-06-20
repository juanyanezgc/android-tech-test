package com.theiconic.android.shop.catalog;

import com.google.gson.annotations.SerializedName;
import com.theiconic.android.shop.FacetEntity;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class ProductsEntity {

  @SerializedName("page_count")
  private int pageCount;

  @SerializedName("page_size")
  private int pageSize;

  @SerializedName("total_items")
  private int totalItems;

  @SerializedName("_embedded")
  private Embedded embedded;

  public List<FacetEntity> getFacets() {
    if (embedded != null && embedded.getProducts() != null) {
      return embedded.getFacets();
    }
    return Collections.emptyList();
  }


  public List<CatalogProductEntity> getProducts() {
    if (embedded != null && embedded.getProducts() != null) {
      return embedded.getProducts();
    }
    return Collections.emptyList();
  }

  @Data
  public static class Embedded {
    @SerializedName("product")
    private List<CatalogProductEntity> products;

    @SerializedName("facets")
    private List<FacetEntity> facets;


  }
}
