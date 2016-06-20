package com.theiconic.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.theiconic.android.shop.catalog.domain.repositories.ProductsRepository;

public class MainActivity extends AppCompatActivity {

  ProductsRepository repo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
