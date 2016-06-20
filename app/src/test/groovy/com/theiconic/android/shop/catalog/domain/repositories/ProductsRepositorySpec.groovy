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
    def link = 'https://eve.theiconic.com.au/v1/catalog/products?gender=male&shop=sports&category=5'
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

    def productListOne = [mockProduct("one"), mockProduct("two")]
    def productsOne = mockProducts(productListOne, 100)

    def productListTwo = [mockProduct("three"), mockProduct("four")]
    def productsTwo = mockProducts(productListTwo, 100)

    def link = 'https://eve.theiconic.com.au/v1/catalog/products?gender=male&shop=sports&category=5'

    when:
    repository.getProducts().subscribe(subscriber)
    repository.load(link)

    then: 'we load the first page of products'
    1 * service.getProducts(_) >> { args ->
      HashMap<String, String> queryParams = (HashMap) args[0]
      assert queryParams.get("page") == "1"
      return Observable.just(productsOne)
    }
    subscriber.assertValue(productListOne)

    when: 'we request the next'
    repository.nextPage()

    then: 'we load the second page of products from the service and combine with the previous products'
    1 * service.getProducts(_) >> { args ->
      HashMap<String, String> queryParams = (HashMap) args[0]
      assert queryParams.get("page") == "2"
      return Observable.just(productsTwo)
    }

    subscriber.onNextEvents.size() == 2
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
