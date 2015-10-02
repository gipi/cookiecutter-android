package br.com.quintoandar.template.di.module;

import android.content.Context;

import com.octo.android.robospice.SpiceManager;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import br.com.quintoandar.template.QuintoandarApplication;
import br.com.quintoandar.template.network.QuintoandarService;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class ApplicationModule {

    private final QuintoandarApplication application;

    public ApplicationModule(QuintoandarApplication application) {
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

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getInstance(application);
    }

    @Provides
    @Singleton
    SpiceManager provideSpice() {
        return new SpiceManager(QuintoandarService.class);
    }
}
