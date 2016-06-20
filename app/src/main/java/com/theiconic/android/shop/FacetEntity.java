package com.theiconic.android.shop;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class FacetEntity {

  @SerializedName("name")
  private String name;

  @SerializedName("param")
  private String param;

  @SerializedName("display")
  private boolean display;

  @SerializedName("combine")
  private String combine;

  @SerializedName("weight")
  private int weight = 0;


  private List<FacetValueEntity> values = null;


  private HashMap<String, List<FacetValueEntity>> groupedValues = null;
}
