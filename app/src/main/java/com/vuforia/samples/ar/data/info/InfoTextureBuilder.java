package com.vuforia.samples.ar.data.info;

import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.models.ProductInfo;

/**
 * Created by grishberg on 15.04.17.
 * Интерфейс из данных делает текстуру, которая наложится на панель с информацией
 */

public interface InfoTextureBuilder {
    Texture getTextureBitmapFromInfo(ProductInfo productInfo);
}
