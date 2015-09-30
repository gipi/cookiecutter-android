package com.company.templateapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.company.templateapplication.OrganizationApplication;
import com.company.templateapplication.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        OrganizationApplication.from(this).getComponent().inject(this);
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

    @OnClick(R.id.button)
    protected void onClick() {
        Timber.i("Button clicked!");
    }
}
