package br.com.quintoandar.template.di.component;

import br.com.quintoandar.template.QuintoandarApplication;
import br.com.quintoandar.template.activity.MainActivity;
import br.com.quintoandar.template.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(QuintoandarApplication application);

    void inject(MainActivity activity);
}
