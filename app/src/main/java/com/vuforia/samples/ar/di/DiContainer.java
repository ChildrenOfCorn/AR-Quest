package com.vuforia.samples.ar.di;

import com.vuforia.samples.ar.data.info.InfoTextureBuilder;
import com.vuforia.samples.ar.data.info.ProductInfoInteractor;
import com.vuforia.samples.ar.data.info.ProductInfoTextureManager;
import com.vuforia.samples.ar.data.info.ProductInfoTextureManagerImpl;
import com.vuforia.samples.ar.data.info.RemoteProductInfoInteractor;
import com.vuforia.samples.ar.di.components.AppComponent;
import com.vuforia.samples.ar.network.HtmlBitmapGenerator;

/**
 * Created by grishberg on 15.04.17.
 * контейнер зависимостей
 */

public class DiContainer {

    public static ProductInfoTextureManager provideProductInfoManager() {
        return new ProductInfoTextureManagerImpl(provideInfoTextureBuilder());
    }

    public static ProductInfoInteractor provideProductInfoInteractor() {
        return new RemoteProductInfoInteractor();
    }

    public static InfoTextureBuilder provideInfoTextureBuilder() {
        return new HtmlBitmapGenerator();
    }

    private static AppComponent appComponent;

    public static void initComponents(final AppComponent component) {
        appComponent = component;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
