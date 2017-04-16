package com.vuforia.samples.ar.repository.auth;

/**
 * Created by grishberg on 16.04.17.
 */

import com.vuforia.samples.ar.repository.AuthTokenRepository;
import com.vuforia.samples.ar.rest.Api;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by grishberg on 12.01.17.
 * Репозиторий авторизации
 */
public class AuthRepositoryImpl implements AuthRepository {

    private final Api api;
    private final AuthTokenRepository authRepository;

    public AuthRepositoryImpl(final Api api, final AuthTokenRepository authRepository) {
        this.api = api;
        this.authRepository = authRepository;
    }

    @Override
    public Observable<Boolean> auth(final String name) {
        return api.auth(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    // сохранить в хранилище токен авторизации
                    authRepository.setCurrentLogin(name);
                    authRepository.setAuthInfo(response.getData());
                    return Observable.just(true);
                });
    }
}