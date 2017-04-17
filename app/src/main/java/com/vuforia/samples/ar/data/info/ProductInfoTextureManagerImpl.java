package com.vuforia.samples.ar.data.info;

import android.support.annotation.NonNull;

import com.vuforia.State;
import com.vuforia.Trackable;
import com.vuforia.TrackableResult;
import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.common.LogService;
import com.vuforia.samples.ar.data.beans.ObjectInfo;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.di.DiContainer;
import com.vuforia.samples.ar.repository.SimpleCallback;
import com.vuforia.samples.ar.repository.info.ProductInfoRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

/**
 * Created by grishberg on 16.04.17.
 */

public class ProductInfoTextureManagerImpl implements ProductInfoTextureManager {
    private static final String TAG = ProductInfoTextureManagerImpl.class.getSimpleName();
    private final Map<Long, Texture> textureMap = new ConcurrentHashMap<>();
    private final Map<Long, ProductInfo> productInfoMap = new ConcurrentHashMap<>();
    private final Set<Long> loadingProductInfoQueue = new HashSet<>();

    @Inject
    LogService log;

    @Inject
    ProductInfoRepository repository;

    private final InfoTextureBuilder textureBuilder;
    private final SimpleCallback<ProductInfo> productInfoCallback = new SimpleCallback<ProductInfo>() {
        @Override
        public void onSuccess(final ProductInfo result) {
            long objectId = result.getId();
            log.d(TAG, "requestProductInfo success for " + objectId);
            productInfoMap.put(objectId, result);
            loadingProductInfoQueue.remove(objectId);
            textureBuilder.getTextureBitmapFromInfo(result, new SimpleCallback<Texture>() {
                @Override
                public void onSuccess(Texture texture) {
                    log.d(TAG, "getTextureBitmapFromInfo success for " + objectId);
                    textureMap.put(objectId, texture);
                }

                @Override
                public void onFail(Throwable error) {
                    //not handled
                }
            });
        }

        @Override
        public void onFail(Throwable error) {
            log.e(TAG, "requestProductInfo onFail", error);
        }
    };

    public ProductInfoTextureManagerImpl(InfoTextureBuilder textureBuilder) {
        this.textureBuilder = textureBuilder;
        DiContainer.getAppComponent().inject(this);
    }

    @Override
    public void updateRecognizedObjects(State state) {
        Map<Long, Texture> hiddenObjects = new HashMap<>(textureMap);

        for (int i = 0; i < state.getNumTrackableResults(); i++) {
            TrackableResult result = state.getTrackableResult(i);
            Trackable trackable = result.getTrackable();
            ObjectInfo objectInfo = (ObjectInfo) trackable.getUserData();
            hiddenObjects.remove(objectInfo.getId());
        }
        removeHiddenObjects(hiddenObjects);
    }

    private void removeHiddenObjects(Map<Long, Texture> hiddenObjects) {
        for (Map.Entry<Long, Texture> entry : hiddenObjects.entrySet()) {
            textureMap.remove(entry.getKey());
            productInfoMap.remove(entry.getKey());
        }
    }

    @Override
    public ProductInfo getProductInfoForObject(ObjectInfo objectInfo) {
        return productInfoMap.get(objectInfo.getId());
    }

    @Override
    public Texture getTextureForObject(@NonNull ObjectInfo objectInfo) {
        final long objectId = objectInfo.getId();
        Texture textureForObject = textureMap.get(objectId);
        if (textureForObject == null && !loadingProductInfoQueue.contains(objectId)) {
            log.d(TAG, "getTextureForObject found new object with id = " + objectId);
            loadingProductInfoQueue.add(objectId);
            repository.requestProductInfo(objectId, productInfoCallback);
        }
        return textureForObject;
    }
}
