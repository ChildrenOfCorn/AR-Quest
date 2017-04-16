package com.vuforia.samples.ar.data.info;

import com.vuforia.samples.ar.common.LogService;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.di.DiContainer;
import com.vuforia.samples.ar.repository.SimpleCallback;
import com.vuforia.samples.ar.repository.info.ProductInfoRepository;

import javax.inject.Inject;

/**
 * Created by grishberg on 16.04.17.
 */

public class RemoteProductInfoInteractor implements ProductInfoInteractor, SimpleCallback<ProductInfo> {
    private static String TAG = RemoteProductInfoInteractor.class.getSimpleName();
    @Inject
    ProductInfoRepository productInfoRepository;

    @Inject
    LogService log;

    private OnProductReceivedListener listener;

    public RemoteProductInfoInteractor() {
        DiContainer.getAppComponent().inject(this);
    }

    @Override
    public void getProductInfoByTargetId(long recognizedTargetId) {
        productInfoRepository.requestProductInfo(recognizedTargetId, this);
    }

    @Override
    public void setOnProductReceivedListener(OnProductReceivedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(ProductInfo result) {
        if (listener != null) {
            listener.onProductInfoReceived(result);
        }
    }

    @Override
    public void onFail(Throwable error) {
        log.e(TAG, "onFail", error);
    }
}
