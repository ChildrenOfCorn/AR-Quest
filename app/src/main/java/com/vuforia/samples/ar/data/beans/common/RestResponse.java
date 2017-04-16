package com.vuforia.samples.ar.data.beans.common;

/**
 * Created by grishberg on 16.04.17.
 */

public class RestResponse<T> {
    private final boolean isCached;

    private final T data;

    private RestError error;

    public RestResponse(final T data) {
        this.data = data;
        this.isCached = false;
    }

    public RestResponse(final T data, final boolean isCached) {
        this.data = data;
        this.isCached = isCached;
    }

    public boolean isCached() {
        return isCached;
    }

    public T getData() {
        return data;
    }

    public RestError getError() {
        return error;
    }
}

