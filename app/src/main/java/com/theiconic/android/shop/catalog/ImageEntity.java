package com.theiconic.android.shop.catalog;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImageEntity {

  @NonNull
  @SerializedName("url")
  private String url;

  @SerializedName("orientation")
  private String orientation;

  @SerializedName("sprite")
  private String sprite;

  @SerializedName("thumbnail")
  private String thumbnail;
}
