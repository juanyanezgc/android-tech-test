package com.theiconic.android.shop.product.entities;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NonNull;

@Data
public class ShopEntity {

  @NonNull
  @SerializedName("name")
  private String name;

  @SerializedName("is_default")
  private boolean isDefault;

}
