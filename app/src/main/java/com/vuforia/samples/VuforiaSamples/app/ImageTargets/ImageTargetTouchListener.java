package com.vuforia.samples.VuforiaSamples.app.ImageTargets;

import android.support.annotation.Nullable;

import com.vuforia.samples.ar.data.beans.ProductInfo;

interface ImageTargetTouchListener {

    @Nullable
    ProductInfo getCurrentProductInfo();

    void onDetailsClicked(ProductInfo productInfo);

    void onAutofocusRequested();
}
