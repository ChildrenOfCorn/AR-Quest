package com.vuforia.samples.VuforiaSamples.app.ImageTargets;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.vuforia.samples.ar.data.models.ProductInfo;

class ImageTargetGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final String LOG_TAG = "ImageTargetTouch";

    private ImageTargetTouchListener touchListener;

    ImageTargetGestureListener(ImageTargetTouchListener listener) {
        touchListener = listener;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        ProductInfo productInfo = touchListener.getCurrentProductInfo();
        if (productInfo == null || touchListener == null) {
            return false;
        }

        touchListener.onDetailsClicked(productInfo);

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (touchListener != null) {
            touchListener.onAutofocusRequested();
            return true;
        }

        return false;
    }

}
