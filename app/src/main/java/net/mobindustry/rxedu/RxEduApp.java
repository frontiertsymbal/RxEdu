package net.mobindustry.rxedu;

import android.app.Application;

import net.mobindustry.rxedu.briteDb.Db;

import butterknife.ButterKnife;

public class RxEduApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ButterKnife.setDebug(BuildConfig.DEBUG);
        Db.initDataBase(this);
    }

}
