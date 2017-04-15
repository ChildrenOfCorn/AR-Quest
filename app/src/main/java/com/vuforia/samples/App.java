package com.vuforia.samples;

import android.app.Application;
import android.content.Context;

/**
 * Created by grishberg on 15.04.17.
 */

public class App extends Application {
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        App.appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
