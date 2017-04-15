package com.vuforia.samples.ar.data.info;

import android.support.annotation.Nullable;

import com.vuforia.samples.ar.data.models.ProductInfo;

/**
 * Created by grishberg on 15.04.17.
 * Возвращение информации по идентификатору
 */

public interface ProductInfoInteractor {
    @Nullable
    ProductInfo getProductInfoByTargetId(long recognizedTargetId);
}
