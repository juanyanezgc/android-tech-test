package com.theiconic.android.shop.catalog;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NonNull;

@Data
public class BrandEntity {

  @NonNull
  @SerializedName("url_key")
  private String urlKey;

  @NonNull
  @SerializedName("name")
  private String name;

}
