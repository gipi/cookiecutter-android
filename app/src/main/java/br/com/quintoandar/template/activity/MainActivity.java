package br.com.quintoandar.template.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import br.com.quintoandar.template.QuintoandarApplication;
import br.com.quintoandar.template.R;
import br.com.quintoandar.template.event.SomeEvent;
import br.com.quintoandar.template.network.QuintoandarService;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
