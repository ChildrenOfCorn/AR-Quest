package com.vuforia.samples.ar.data.info;

import com.vuforia.State;
import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.beans.ObjectInfo;
import com.vuforia.samples.ar.data.beans.ProductInfo;

/**
 * Created by grishberg on 16.04.17.
 */

public interface ProductInfoTextureManager {
    void updateRecognizedObjects(State state);

    ProductInfo getProductInfoForObject(ObjectInfo objectInfo);

    Texture getTextureForObject(ObjectInfo objectInfo);
}
