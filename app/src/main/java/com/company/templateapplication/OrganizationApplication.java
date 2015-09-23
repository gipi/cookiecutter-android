package com.company.templateapplication;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;

public class OrganizationApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Hawk.init(this)
            .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
            .setStorage(HawkBuilder.newSqliteStorage(this))
            .build();
    }
}
