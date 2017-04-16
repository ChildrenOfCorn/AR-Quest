package com.vuforia.samples.ar.di.components;

/**
 * Created by grishberg on 16.04.17.
 */

import com.vuforia.samples.ar.di.modules.AppModule;
import com.vuforia.samples.ar.di.modules.RestModule;
import com.vuforia.samples.VuforiaSamples.app.ImageTargets.AuthActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by grishberg on 12.01.17.
 * Компонент Dagger
 */
@Singleton
@Component(modules = {RestModule.class,
        AppModule.class
})

public interface AppComponent {

    void inject(AuthActivity authActivity);
}