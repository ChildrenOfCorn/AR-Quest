package com.vuforia.samples.ar.data.info;

import android.support.annotation.NonNull;

import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.repository.SimpleCallback;

/**
 * Created by grishberg on 15.04.17.
 * Интерфейс из данных делает текстуру, которая наложится на панель с информацией
 */

public interface InfoTextureBuilder {
    void getTextureBitmapFromInfo(@NonNull ProductInfo productInfo, SimpleCallback<Texture> callback);
}