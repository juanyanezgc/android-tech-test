package com.theiconic.android.shop.catalog.domain.repositories;

import com.theiconic.android.shop.catalog.CatalogProductEntity;
import com.theiconic.android.shop.catalog.ProductsEntity;
import com.theiconic.android.shop.categories.entities.CategoryQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.subjects.BehaviorSubject;

public class ProductsRepository {

    private static final int INITIAL_PAGE = 1;
    private static final String PARAM_PAGE_SIZE = "page_size";
    private static final String PARAM_PAGE = "page";

    private final int pageSize;
    private final ProductsService service;
    private final Scheduler scheduler;

    private BehaviorSubject<List<CatalogProductEntity>> subject;
    private CategoryQuery query;
    private int page;
    private boolean isNewQuery;


    public ProductsRepository(ProductsService service, Scheduler scheduler, int pageSize) {
        this.service = service;
        this.scheduler = scheduler;
        this.pageSize = pageSize;
    }

    public Observable<List<CatalogProductEntity>> getProducts() {
        if (subject == null) {
            subject = BehaviorSubject.create();
        }
        return subject.asObservable();
    }

    public void load(String link) {
        query = new CategoryQuery(link);
        page = INITIAL_PAGE;
        isNewQuery = true;
        requestPage();
    }

    public void nextPage() {
        page++;
        requestPage();
    }

    private void requestPage() {
        Map<String, String> queryMap = query.getQueryMap();
        queryMap.put(PARAM_PAGE_SIZE, String.valueOf(pageSize));
        queryMap.put(PARAM_PAGE, String.valueOf(page));

        service.getProducts(queryMap)
                .map(this::combineResults)
                .subscribeOn(scheduler)
                .subscribe(this::notifySubscribers, this::onError);
    }


    private List<CatalogProductEntity> combineResults(ProductsEntity productsEntity) {
        //If it's a new query (meaning that a different link to the previously one has been loaded) we discard the previous items as they are different queries
        List<CatalogProductEntity> products = isNewQuery ? new ArrayList<>() : subject.getValue();
        products.addAll(productsEntity.getProducts());
        isNewQuery = false;
        return products;
    }

    private void notifySubscribers(List<CatalogProductEntity> products) {
        subject.onNext(products);
    }

    private void onError(Throwable throwable) {
        subject.onError(throwable);
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
                    if (i < values.size() - 1) {
                        outValue += ",";
                    }
                }
                output.put(entry.getKey(), "[" + outValue + "]");

            }
        }
        return output;
    }
}
