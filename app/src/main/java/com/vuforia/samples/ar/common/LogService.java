package com.vuforia.samples.ar.common;

/**
 * Created by grishberg on 16.04.17.
 */

public interface LogService {
    void d(String tag, String message);

    void e(String tag, String message);

    void e(String tag, String message, Throwable throwable);
}