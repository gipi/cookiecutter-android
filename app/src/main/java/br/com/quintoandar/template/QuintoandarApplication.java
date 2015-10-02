package br.com.quintoandar.template;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

import br.com.quintoandar.template.core.CrashReportingTree;
import br.com.quintoandar.template.di.component.ApplicationComponent;
import br.com.quintoandar.template.di.component.DaggerApplicationComponent;
import br.com.quintoandar.template.di.module.ApplicationModule;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class QuintoandarApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG ? new DebugTree() : new CrashReportingTree());

        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .build();

        ConcurrentHashMap<String, String> f = new ConcurrentHashMap<>();
        f.put("A", "B");
        Hawk.put("A", f);


        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static QuintoandarApplication from(@NonNull Context context) {
        return (QuintoandarApplication) context.getApplicationContext();
    }
}
