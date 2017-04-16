package com.vuforia.samples.ar.repository.auth;

import com.vuforia.samples.ar.repository.SimpleCallback;

import rx.Observable;

/**
 * Created by grishberg on 16.04.17.
 */

public interface AuthRepository {
    void auth(String name, SimpleCallback<Boolean> callback);
}
