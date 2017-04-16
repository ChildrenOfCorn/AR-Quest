package com.vuforia.samples.ar.repository.auth;

import rx.Observable;

/**
 * Created by grishberg on 16.04.17.
 */

public interface AuthRepository {
    Observable<Boolean> auth(String name);
}
