package com.vuforia.samples.ar.repository.info;

import com.vuforia.samples.ar.data.beans.Comment;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.repository.AuthTokenRepository;
import com.vuforia.samples.ar.repository.SimpleCallback;
import com.vuforia.samples.ar.rest.Api;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by grishberg on 16.04.17.
 */

public class InfoRepositoryImpl implements ProductInfoRepository {

    private final Api api;
    private final AuthTokenRepository authRepository;

    public InfoRepositoryImpl(Api api, AuthTokenRepository authRepository) {
        this.api = api;
        this.authRepository = authRepository;
    }

    @Override
    public void requestProductInfo(long productId, SimpleCallback<ProductInfo> callback) {
        api.requestProductInfo(authRepository.getAuthInfo().getId(), productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    return Observable.just(response.getData());
                })
                .subscribe(response -> {
                            callback.onSuccess(response);
                        },
                        error -> {
                            callback.onFail(error);
                        });
    }

    @Override
    public void postComment(long productId, String commentText, float rating, SimpleCallback<Float> callback) {
        api.postComment(authRepository.getAuthInfo().getId(), productId, commentText, rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    return Observable.just(response.getData());
                })
                .subscribe(response -> {
                            callback.onSuccess(response);
                        },
                        error -> {
                            callback.onFail(error);
                        });
    }

    @Override
    public void requestComments(long productId, SimpleCallback<List<Comment>> callback) {
        api.requestComments(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    return Observable.just(response.getData());
                })
                .subscribe(response -> {
                            callback.onSuccess(response);
                        },
                        error -> {
                            callback.onFail(error);
                        });
    }
}
