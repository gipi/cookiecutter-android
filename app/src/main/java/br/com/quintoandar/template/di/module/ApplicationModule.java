package br.com.quintoandar.template.di.module;

import android.content.Context;

import br.com.quintoandar.template.QuintoandarApplication;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

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
}
