package com.company.templateapplication.di.module;

import android.content.Context;

import com.company.templateapplication.OrganizationApplication;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final OrganizationApplication application;

    public ApplicationModule(OrganizationApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
