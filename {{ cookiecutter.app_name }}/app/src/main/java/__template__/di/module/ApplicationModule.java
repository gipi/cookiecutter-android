package {{ cookiecutter.package_name }}.di.module;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import {{ cookiecutter.package_name }}.BuildConfig;
import {{ cookiecutter.package_name }}.{{ cookiecutter.base_name }}Application;
import {{ cookiecutter.package_name }}.network.{{ cookiecutter.base_name }}Service;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApplicationModule {

    private final {{ cookiecutter.base_name }}Application application;

    public ApplicationModule({{ cookiecutter.base_name }}Application application) {
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
    {{ cookiecutter.base_name }}Service provideService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create({{ cookiecutter.base_name }}Service.class);
    }
}
