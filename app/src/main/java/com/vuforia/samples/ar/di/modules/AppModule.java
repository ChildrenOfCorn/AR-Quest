package com.vuforia.samples.ar.di.modules;

import android.content.Context;
import android.content.res.Resources;

import com.vuforia.samples.App;
import com.vuforia.samples.ar.common.LogService;
import com.vuforia.samples.ar.common.LogServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grishberg on 16.04.17.
 */

@Module
public class AppModule {
    private final App app;

    public AppModule(final App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return app.getResources();
    }

    @Provides
    @Singleton
    LogService provideLogService() {
        return new LogServiceImpl();
    }
}
