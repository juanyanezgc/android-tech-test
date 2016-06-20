#THE ICONIC ANDRIOD TECH TEST
##REACTIVE REPOSITORY
<b>Goal : </b>Make the unit test green<br/>
<b>Class : </b> com.theiconic.android.shop.catalog.domain.repositories.ProductsRepository<br/>
<b>Unit Test : </b>com.theiconic.android.shop.catalog.domain.repositories.ProductsRepositorySpec<br/>
<b>Overview:</b> <br/>
We have an empty <i>ProductsRepository</i> Class. Its responsibility is to abstract the behavior of loading and persisting products for a given category. In this case we want to only deal with loading directly from the service and not cache anything.<br/>

It is designed to be [reactive](https://en.wikipedia.org/wiki/Reactive_programming), and uses [RxJava](https://github.com/ReactiveX/RxJava) to help achieve this. Because it is reactive when we call <i>nextPage</i>, we do not have to then make another call to <i>getItems</i>; subscribers to <i>getItems</i> should receive another event with all items loaded so far.<br/>

<b>ProductService : </b> ProductService is an interface for our REST endpoint. It takes a number of parameters but the only ones you need to pass to it are page and page_size which are integer values. You wont be hitting the actual endpoint though only the mocked interface in the unit test.

<b>Rules : </b> Do not modify the Unit Test. You should beable to make it pass without changing the test. Because of this you will not need to change the ProductRepository API but can add as many protected / private methods as needed.

<b>Tips : </b>You may want to use [BehaviorSubjects](http://reactivex.io/RxJava/javadoc/rx/subjects/BehaviorSubject.html) in your implementation but try to minimize their usage. Try to minimze state.

<b>Technologies : </b> The Unit test are written in [Groovy](https://en.wikipedia.org/wiki/Groovy_(programming_language)) using [Spok](http://spockframework.github.io/spock/docs/1.0/index.html); [this section is the most helpful](http://spockframework.github.io/spock/docs/1.0/interaction_based_testing.html)<br />
[RxJava](https://github.com/ReactiveX/RxJava) is used to create flows of data and help our object become reactive.



