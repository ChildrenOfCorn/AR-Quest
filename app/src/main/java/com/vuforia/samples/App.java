package com.vuforia.samples;

import android.app.Application;
import android.content.Context;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.ar.di.DiContainer;
import com.vuforia.samples.ar.di.components.DaggerAppComponent;
import com.vuforia.samples.ar.di.modules.AppModule;
import com.vuforia.samples.ar.di.modules.RestModule;

/**
 * Created by grishberg on 15.04.17.
 */

public class App extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        App.appContext = getApplicationContext();
        DiContainer.initComponents(DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .restModule(new RestModule(getString(R.string.end_point)))
                .build()
        );
    }

    public static Context getAppContext() {
        return appContext;
    }
}
