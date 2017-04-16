package com.vuforia.samples.ar.repository.info;

import com.vuforia.samples.ar.repository.SimpleCallback;

/**
 * Created by grishberg on 16.04.17.
 */

public interface InfoRepository {
    void requestProductInfo(long productId, SimpleCallback<Boolean> callback);
}
