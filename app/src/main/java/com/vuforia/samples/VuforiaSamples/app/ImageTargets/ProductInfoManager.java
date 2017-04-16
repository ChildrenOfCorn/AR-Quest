package com.vuforia.samples.VuforiaSamples.app.ImageTargets;

import com.vuforia.State;
import com.vuforia.Trackable;
import com.vuforia.TrackableResult;
import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.beans.ObjectInfo;
import com.vuforia.samples.ar.data.beans.ProductInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by grishberg on 16.04.17.
 */

public class ProductInfoManager {
    private final Map<Long, ProductInfo> productInfoMap = new ConcurrentHashMap<>();
    private final Set<Long> productInfoRequestSet = new HashSet<>();

    public void updateOldTextures(State state) {
        Map<Long, ProductInfo> textureBuffer = new HashMap<>(productInfoMap);

        for (int tIdx = 0; tIdx < state.getNumTrackableResults(); tIdx++) {
            TrackableResult result = state.getTrackableResult(tIdx);
            Trackable trackable = result.getTrackable();
            ObjectInfo objectInfo = (ObjectInfo) trackable.getUserData();
            textureBuffer.remove(objectInfo.getId());
        }
        for (Map.Entry<Long, ProductInfo> entry : textureBuffer.entrySet()) {
            productInfoMap.remove(entry.getKey());
        }
    }

    public boolean checkProductInfo(ObjectInfo objectInfo) {
        ProductInfo productInfo = productInfoMap.get(objectInfo.getId());
        if (productInfo != null || productInfoRequestSet.contains(objectInfo.getId())) {
            return true;
        }
        productInfoRequestSet.add(objectInfo.getId());
        return false;
    }

    public ProductInfo getProductInfo(ObjectInfo objectInfo) {
        return productInfoMap.get(objectInfo.getId());
    }

    public void putProductInfo(long id, ProductInfo result) {
        productInfoMap.put(id, result);
        productInfoRequestSet.remove(id);
    }
}
