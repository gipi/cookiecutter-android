![Image credits: http://squarerootofpie.com/2012/06/19/googley](cookiecutter.jpg)

### This repository contains the scaffold for all QuintoAndar android applications



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

#### Batteries Included

* [Retrolambda](https://github.com/orfjackal/retrolambda) & [Lightweight Stream](https://github.com/aNNiMON/Lightweight-Stream-API) bring the Java 8 streams and lambda to Java 7.
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
