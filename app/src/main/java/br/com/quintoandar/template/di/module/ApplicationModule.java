package br.com.quintoandar.template.di.module;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import br.com.quintoandar.template.BuildConfig;
import br.com.quintoandar.template.QuintoandarApplication;
import br.com.quintoandar.template.network.QuintoandarService;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
    QuintoandarService provideService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(QuintoandarService.class);
    }
}
