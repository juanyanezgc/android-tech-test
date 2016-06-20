package com.theiconic.android.shop.catalog.domain.repositories;

import com.theiconic.android.shop.catalog.ProductsEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ProductsService {

  @Headers("Accept: application/json")
  @GET("catalog/products")
  Observable<ProductsEntity> getProducts(@QueryMap Map<String, String> query);

  @Headers("Accept: application/json")
  @GET("catalog/products/{urlKey}")
  Observable<ProductsEntity> getProductsWithUrlKey(@Path("urlKey") String category, @QueryMap Map<String, String> query);
}
