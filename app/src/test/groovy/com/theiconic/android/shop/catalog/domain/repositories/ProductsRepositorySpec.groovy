package com.theiconic.android.shop.catalog.domain.repositories


import com.theiconic.android.shop.catalog.CatalogProductEntity
import com.theiconic.android.shop.catalog.ProductsEntity
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import rx.Observable
import spock.lang.Specification

class ProductsRepositorySpec extends Specification {
  final static int PAGE_SIZE = 2
  ProductsRepository repository
  ProductsService service

  def setup() {
    service = Mock(ProductsService)
    repository = new ProductsRepository(service, Schedulers.immediate(),PAGE_SIZE);
    repository
  }

  def 'load() - loads products'(){
    given: 'a service that returns products'
    def subscriber = new TestSubscriber<List<CatalogProductEntity>>()
    def productList = [Mock(CatalogProductEntity),Mock(CatalogProductEntity)]
    def products = Mock(ProductsEntity){
      it.getProducts() >> productList
      it.getPageCount() >> 100
    }
    def link = 'https://eve.theiconic.com.au/v1/catalog/products?gender=male&shop=sports&category=5';
    service.getProducts(_) >> Observable.just(products)

    when: 'we subscribe to getProducts and call load'
    repository.getProducts().subscribe(subscriber)
    repository.load(link)

    then:'we receive the products from the service'
    subscriber.assertValue(productList)
  }

  def 'nextPage() - causes load of second page and combines results of previous pages'(){
    given:
    def subscriber = new TestSubscriber<List<CatalogProductEntity>>()

    def productListOne = [mockProduct("one"),mockProduct("two")]
    def productsOne = mockProducts(productListOne,100)

    def productListTwo = [mockProduct("three"),mockProduct("four")]
    def productsTwo = mockProducts(productListTwo,100)

    def link = 'https://eve.theiconic.com.au/v1/catalog/products?gender=male&shop=sports&category=5';

    when:'we subscribe to getProducts and call load'
    repository.getProducts().subscribe(subscriber)
    repository.load(link)

    then:'we receive the products from the service'
    1 * service.getProducts(_) >> Observable.just(productsOne)
    subscriber.assertValue(productListOne)

    when: 'we call the next page'
    repository.nextPage()

    then:'we receive another call to the service and the result combines the previous results'
    1 * service.getProducts(_) >> Observable.just(productsTwo)

    subscriber.onNextEvents.size() ==2
    subscriber.onNextEvents[1].size() == 4
    subscriber.onNextEvents[1][0].name == "one"
    subscriber.onNextEvents[1][1].name == "two"
    subscriber.onNextEvents[1][2].name == "three"
    subscriber.onNextEvents[1][3].name == "four"
  }

  CatalogProductEntity mockProduct(String name){
    return Mock(CatalogProductEntity){
      it.getName() >> name
    }
  }

  ProductsEntity mockProducts(List<CatalogProductEntity> products,int pages){
    return Mock(ProductsEntity){
      it.getProducts() >> products
      it.getPageCount() >> pages
    }
  }
}
