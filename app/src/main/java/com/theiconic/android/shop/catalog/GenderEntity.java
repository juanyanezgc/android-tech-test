package com.theiconic.android.shop.catalog;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NonNull;

@Data
public class GenderEntity {

  @SerializedName("id")
  private int id = -1;

  @NonNull
  @SerializedName("name")
  private String name;

}
