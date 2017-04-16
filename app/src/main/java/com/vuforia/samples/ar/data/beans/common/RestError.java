package com.vuforia.samples.ar.data.beans.common;

/**
 * Created by grishberg on 16.04.17.
 */

public class RestError {
    private static final String TAG = RestError.class.getSimpleName();
    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

