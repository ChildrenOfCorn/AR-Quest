package com.vuforia.samples.ar.rest;

import com.vuforia.samples.ar.data.beans.AuthContainer;
import com.vuforia.samples.ar.data.beans.Comment;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.data.beans.common.RestResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by grishberg on 16.04.17.
 */

public interface Api {
    @POST("registerUser")
    Observable<RestResponse<AuthContainer>> auth(@Query("name") String name);

    @GET("getProductInfo")
    Observable<RestResponse<ProductInfo>> requestProductInfo(@Query("userId") long userId,
                                                             @Query("productId") long productId);

    @POST("postComment")
    Observable<RestResponse<Float>> postComment(@Query("userId") long userId,
                                                      @Query("productId") long productId,
                                                      @Query("commentText") String commentText,
                                                      @Query("rating") float rating);

    @POST("registerUser")
    Observable<RestResponse<List<Comment>>> requestComments(@Query("productId") long productId);

}
