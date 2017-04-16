package com.vuforia.samples.ar.common.data.rest;

import com.vuforia.samples.ar.common.data.rest.exceptions.RetrofitException;
import com.vuforia.samples.ar.data.beans.common.RestResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by grishberg on 27.11.16.
 */
public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJavaCallAdapterFactory original;
    private final SoftErrorDelegate<RestResponse> softErrorDelegate;

    private RxErrorHandlingCallAdapterFactory(final SoftErrorDelegate<RestResponse> softErrorDelegate) {
        this.softErrorDelegate = softErrorDelegate;
        original = RxJavaCallAdapterFactory.create();
    }

    public static CallAdapter.Factory create(final SoftErrorDelegate<RestResponse> softErrorDelegate) {
        return new RxErrorHandlingCallAdapterFactory(softErrorDelegate);
    }

    @Override
    public CallAdapter<?> get(final Type returnType, final Annotation[] annotations, final Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit,
                original.get(returnType, annotations, retrofit),
                softErrorDelegate);
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;
        private final SoftErrorDelegate<RestResponse> softErrorDelegate;

        public RxCallAdapterWrapper(final Retrofit retrofit,
                                    final CallAdapter<?> wrapped,
                                    final SoftErrorDelegate<RestResponse> softErrorDelegate) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
            this.softErrorDelegate = softErrorDelegate;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt(final Call<R> call) {
            return ((Observable<RestResponse<?>>) wrapped.adapt(call))
                    .flatMap(new Func1<RestResponse<?>, Observable<RestResponse<?>>>() {
                        @Override
                        public Observable<RestResponse<?>> call(final RestResponse<?> restResponse) {
                            final Throwable throwable = softErrorDelegate.checkSoftError(restResponse);
                            if (throwable != null) {
                                return Observable.error(throwable);
                            }
                            return Observable.just(restResponse);
                        }
                    });
        }

        private RetrofitException asRetrofitException(final Throwable throwable) {
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                final HttpException httpException = (HttpException) throwable;
                final Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            }
            // A network error happened
            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error

            return RetrofitException.unexpectedError(throwable);
        }
    }
}
