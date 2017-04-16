package com.vuforia.samples.ar.rest;

import android.support.annotation.NonNull;

import com.vuforia.samples.ar.common.data.rest.SoftErrorDelegate;
import com.vuforia.samples.ar.data.beans.common.RestResponse;
import com.vuforia.samples.ar.repository.exception.TokenExpiredException;
import com.vuforia.samples.ar.repository.exception.WrongCredentialsException;

/**
 * Created by grishberg on 16.04.17.
 */

public class ErrorCheckerImpl implements SoftErrorDelegate<RestResponse> {

    @Override
    public Throwable checkSoftError(@NonNull final RestResponse body) {
        if (body.getError() == null) {
            return null;
        }
        switch (body.getError().getCode()) {
            case RestConst.Errors.WRONG_CREDENTIALS:
                return new WrongCredentialsException(body.getError());
            case RestConst.Errors.TOKEN_EXPIRED:
                return new TokenExpiredException(body.getError());
            default:
                return null;
        }
    }
}