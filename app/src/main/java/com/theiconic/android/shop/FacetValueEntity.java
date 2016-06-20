package com.theiconic.android.shop;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class FacetValueEntity {

  @SerializedName("id")
  private Integer id;

  @SerializedName("name")
  private String name;

  @SerializedName("key")
  private String key;

  @SerializedName("count")
  private Integer count;

  @SerializedName("swatch")
  private String swatch;

  @SerializedName("system")
  private String system;
}
