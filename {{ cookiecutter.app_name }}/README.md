![](cookiecutter.jpg)

# This repository contains the scaffold for all QuintoAndar Android applications

## Networking & RESTful
For networking we use reactive programming approach, achieved by RxAndroid/RxJava. First, we
define an interface called QuintoandarService which define the REST entry points used by Retrofit. e.g.:

```java
public interface QuintoandarService {
    @GET("/{id}")
    Observable<DataEnvelope> fetchData(@Path("id") Long id);
}
```

Note the `Observable`, we define that result of the operation is an observable by RxAndroid, with this,
schedulers can be used to work with the entire operation in background or in any scheduler and, at the
end of operation, can be listen on main thread. e.g.:

```java
final Long id = 123L;

quintoandar.fetchData(id)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        result -> // Update some data
        exception -> // Handle the exception
    );
```

Also, you can apply any type of data flow manipulation of RxAndroid/RxJava with lambda in an functional style!

## Database
Instead of SQLite or Core Data, we use [Realm](https://realm.io/). Realm is a database focused on mobile devices, with very fast performance with zero copy and a nice API. Creating a model is very simple, just extends the RealmObject and add some properties. e.g.:

```java
public class User extends RealmObject {

    @PrimaryKey
    private String name;

    private int age;

    @Ignore
    private int dontPersistMe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

> Unfortunately, for now it's impossible to use Lambok to eliminate all getters and setters, see this [issue](https://github.com/realm/realm-java/issues/502)

Below an example on how to persist the data:

```java
realm.beginTransaction();
User user = realm.createObject(User.class);
user.setName("Rodrigo");
user.setAge(28);
realm.commitTransaction();
```

## Injecting views

Tired of findViewById? With Butter Knife you can remove all boilerplate with simple annotations without use of reflection.

## Dependecy Inject

:syringe:

## Functional

Yes, you can use lambda, streams and reactive from Java8 on Android! Thanks to [Retrolambda](https://github.com/orfjackal/retrolambda) and [Lightweight Stream](https://github.com/aNNiMON/Lightweight-Stream-API)

![](lambda.jpg)

```java
List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
return Stream.of(numbers)
            .map(x -> x * x)
            .reduce((x, y) -> x + y)
            .get();
```

## Events

Otto is an event bus designed to decouple different parts of your application while still allowing them to communicate efficiently.

## Ngrok

Not related to the code itself or Android, [ngrok](https://ngrok.com/) is a great tool, allowing to create reverse tunnels on localhost and inspect all requests between the app and the web server.

## Result

Now your code will look like this :)

```java
public class MainActivity extends AppCompatActivity {

    @Inject
    Bus bus;

    @Inject
    Realm realm;

    @Inject
    QuintoandarService quintoandar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        QuintoandarApplication.from(this).getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        bus.unregister(this);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onSomeEvent(SomeEvent event) {
        Timber.i("onSomeEvent: %s", event.data);
    }

    @OnClick(R.id.button)
    protected void onClick() {
        quintoandar.fetchData(123L)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    result ->
                            Timber.i(result.toString()),

                    exception ->
                            Timber.e(exception, "failed to fetch")
            );

        bus.post(new SomeEvent("Hello world!"));
    }
}
```

## Batteries Included

* [Retrolambda](https://github.com/orfjackal/retrolambda) & [Lightweight Stream](https://github.com/aNNiMON/Lightweight-Stream-API) bring the Java 8 streams and lambda to Java 7/6.
* [Lombok](https://projectlombok.org/) eliminate all boilerplate.
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
* [Data Binding](https://developer.android.com/tools/data-binding/guide.html) let you use declarative layouts and minimize the glue code necessary to bind your application logic and layouts.
* [Retrofit](https://square.github.io/retrofit/) a type-safe HTTP client for Android.
* [OkHttp](https://square.github.io/okhttp/) an HTTP & SPDY client for Android used by Retrofit.
* [Realm](https://realm.io/) very fast (zero copy) ORM cross-platform, with encryption, graph queries, and easy migrations.
* [Butter Knife](https://jakewharton.github.io/butterknife/) field and method binding for Android views which uses annotation processing to generate boilerplate code for you.
* [Dagger2](http://google.github.io/dagger/) a fast dependency injector for Android.
* [Otto](https://square.github.io/otto/) is an event bus designed to decouple different parts of your application while still allowing them to communicate efficiently.
* [Picasso](https://square.github.io/picasso/) a powerful image downloading and caching library for Android.
* [Hawk](https://github.com/orhanobut/hawk) secure, simple key-value storage for Android.
* [Timber](https://github.com/JakeWharton/timber) a logger with a small, extensible API which provides utility on top of Android's normal Log class.

_... To be continued_
