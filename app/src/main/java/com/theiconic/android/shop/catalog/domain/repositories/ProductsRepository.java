package com.theiconic.android.shop.catalog.domain.repositories;

import com.theiconic.android.shop.catalog.CatalogProductEntity;
import com.theiconic.android.shop.categories.entities.CategoryQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;

public class ProductsRepository {

  private final int pageSize;
  protected final ProductsService service;
  protected final Scheduler scheduler;

  protected String query = null;


  public ProductsRepository(ProductsService service, Scheduler scheduler, Integer pageSize) {
    this.service = service;
    this.scheduler = scheduler;
    this.pageSize = pageSize;
  }

  public void load(String link) {
    this.query = new CategoryQuery(link).getQuery();

  }

  public void nextPage() {

  }


  public Observable<List<CatalogProductEntity>> getProducts() {
    return Observable.never();
  }




  protected HashMap<String, String> buildQueries(HashMap<String, List<String>> input) {
    HashMap<String, String> output = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : input.entrySet()) {
      List<String> values = entry.getValue();
      if (values.size() == 1) {
        output.put(entry.getKey(), values.get(0));
      } else if (values.size() > 1) {
        String outValue = "";
        for (int i = 0; i < values.size(); i++) {
          String val = values.get(i);
          outValue += val;
          if(i < values.size() -1) {
            outValue += ",";
          }
        }
        output.put(entry.getKey(), "["+outValue+"]");

      }
    }
    return output;
  }
}
