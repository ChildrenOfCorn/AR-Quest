package com.vuforia.samples.ar.common.data.rest;

/**
 * Created by grishberg on 16.04.17.
 */

public interface SoftErrorDelegate<T> {
    Throwable checkSoftError(T body);
}
