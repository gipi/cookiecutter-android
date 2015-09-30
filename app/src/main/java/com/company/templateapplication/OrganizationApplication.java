package com.company.templateapplication;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.company.templateapplication.core.CrashReportingTree;
import com.company.templateapplication.di.component.ApplicationComponent;
import com.company.templateapplication.di.component.DaggerApplicationComponent;
import com.company.templateapplication.di.module.ApplicationModule;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class OrganizationApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG ? new DebugTree() : new CrashReportingTree());

        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .build();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static OrganizationApplication from(@NonNull Context context) {
        return (OrganizationApplication) context.getApplicationContext();
    }
}
