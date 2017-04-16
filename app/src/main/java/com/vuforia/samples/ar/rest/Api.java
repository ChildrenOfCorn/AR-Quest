package com.vuforia.samples.ar.rest;

import com.vuforia.samples.ar.data.beans.AuthContainer;
import com.vuforia.samples.ar.data.beans.common.RestResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by grishberg on 16.04.17.
 */

public interface Api {
    @POST("auth")
    Observable<RestResponse<AuthContainer>> auth (@Query("name") String name);

}
