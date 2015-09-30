package com.company.templateapplication.di.component;

import com.company.templateapplication.OrganizationApplication;
import com.company.templateapplication.activity.MainActivity;
import com.company.templateapplication.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(OrganizationApplication application);

    void inject(MainActivity activity);
}
