package com.vuforia.samples.ar.repository.info;

import com.vuforia.samples.ar.data.beans.Comment;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.repository.SimpleCallback;

import java.util.List;

/**
 * Created by grishberg on 16.04.17.
 */

public interface ProductInfoRepository {
    void requestProductInfo(long productId, SimpleCallback<ProductInfo> callback);

    void postComment(long productId, String commentText, float rating, SimpleCallback<Float> callback);

    void requestComments(long productId, SimpleCallback<List<Comment>> callback);
}
