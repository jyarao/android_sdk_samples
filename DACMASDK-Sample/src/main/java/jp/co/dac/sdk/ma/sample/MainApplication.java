package jp.co.dac.sdk.ma.sample;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
