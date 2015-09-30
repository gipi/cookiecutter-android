package br.com.quintoandar.template.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.quintoandar.template.QuintoandarApplication;
import br.com.quintoandar.template.R;
import br.com.quintoandar.template.model.Dummy;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    Bus bus;

    @Inject
    Realm realm;

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

    // @Subscribe
    public void onEvent() {
        Timber.i("Button clicked!");
    }

    @OnClick(R.id.button)
    protected void onClick() {
        realm.executeTransaction(realm -> {
            Dummy dummy = new Dummy();
            dummy.setName("foo bar");
            realm.copyToRealmOrUpdate(dummy);
        });

        Timber.i("Button clicked!");
    }
}
