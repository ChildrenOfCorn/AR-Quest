package com.vuforia.samples.ar.di;

import com.vuforia.samples.App;
import com.vuforia.samples.ar.data.info.InfoTextureBuilder;
import com.vuforia.samples.ar.data.info.ProductInfoInteractor;
import com.vuforia.samples.ar.data.info.ProductInfoInteractorStub;
import com.vuforia.samples.ar.network.HtmlBitmapGenerator;

/**
 * Created by grishberg on 15.04.17.
 * контейнер зависимостей
 */

public class DiContainer {
    public static ProductInfoInteractor provideProductInfoInteractor() {
        return new ProductInfoInteractorStub();
    }

    public static InfoTextureBuilder provideInfoTextureBuilder() {
        //return new StubInfoTextureBuilder(App.getAppContext().getAssets());
        return new HtmlBitmapGenerator();
    }
}
