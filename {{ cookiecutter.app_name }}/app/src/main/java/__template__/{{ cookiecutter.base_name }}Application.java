package {{ cookiecutter.package_name }};

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import {{ cookiecutter.package_name }}.core.CrashReportingTree;
import {{ cookiecutter.package_name }}.di.component.ApplicationComponent;
import {{ cookiecutter.package_name }}.di.component.DaggerApplicationComponent;
import {{ cookiecutter.package_name }}.di.module.ApplicationModule;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class {{ cookiecutter.base_name }}Application extends Application {

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
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static {{ cookiecutter.base_name }}Application from(@NonNull Context context) {
        return ({{ cookiecutter.base_name }}Application) context.getApplicationContext();
    }
}
