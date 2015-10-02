package br.com.quintoandar.template.di.component;

import javax.inject.Singleton;

import br.com.quintoandar.template.activity.MainActivity;
import br.com.quintoandar.template.di.module.ApplicationModule;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);
}
