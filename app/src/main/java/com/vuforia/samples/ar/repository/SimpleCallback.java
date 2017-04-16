package com.vuforia.samples.ar.repository;

/**
 * Created by grishberg on 16.04.17.
 */

public interface SimpleCallback<T> {
    void onSuccess(T result);

    void onFail(Throwable error);
}
