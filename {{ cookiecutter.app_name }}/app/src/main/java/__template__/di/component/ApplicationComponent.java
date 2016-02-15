package {{ cookiecutter.package_name }}.di.component;

import javax.inject.Singleton;

import {{ cookiecutter.package_name }}.activity.MainActivity;
import {{ cookiecutter.package_name }}.di.module.ApplicationModule;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);
}
