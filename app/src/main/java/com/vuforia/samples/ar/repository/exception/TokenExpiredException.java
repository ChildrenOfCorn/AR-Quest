package com.vuforia.samples.ar.repository.exception;

import com.vuforia.samples.ar.data.beans.common.RestError;

/**
 * Created by grishberg on 16.04.17.
 */

public class TokenExpiredException extends Throwable {
    public TokenExpiredException(final RestError message) {
        super(message != null ? message.getMessage() : "");
    }
}
