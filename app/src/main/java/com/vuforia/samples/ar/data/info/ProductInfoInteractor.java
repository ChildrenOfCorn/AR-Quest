package com.vuforia.samples.ar.data.info;

import android.support.annotation.NonNull;

import com.vuforia.samples.ar.data.beans.ProductInfo;

/**
 * Created by grishberg on 15.04.17.
 * Возвращение информации по идентификатору
 */

public interface ProductInfoInteractor {
    void getProductInfoByTargetId(long recognizedTargetId);

    void setOnProductReceivedListener(OnProductReceivedListener listener);

    interface OnProductReceivedListener {
        void onProductInfoReceived(@NonNull ProductInfo productInfo);
    }
}
