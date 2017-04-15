package com.vuforia.samples.ar.data.info;

import com.vuforia.samples.ar.data.models.Comment;
import com.vuforia.samples.ar.data.models.ProductInfo;

import java.util.ArrayList;

/**
 * Created by grishberg on 15.04.17.
 */

public class ProductInfoInteractorStub implements ProductInfoInteractor {

    private OnProductReceivedListener listener;

    @Override
    public void getProductInfoByTargetId(long recognizedTargetId) {
        if (listener != null) {
            listener.onProductInfoReceived(createProductInfoFromId(recognizedTargetId));
        }
    }

    private ProductInfo createProductInfoFromId(long id) {
        ProductInfo productInfo = new ProductInfo(id);
        switch ((int) id) {
            case 1:
                productInfo.setName("Кока кола");
                productInfo.setBriefDesc("Очень вкусная");
                productInfo.setUrl("https://ru.wikipedia.org/wiki/Кока-кола");
                break;
            case 2:
                productInfo.setName("Гиннес");
                productInfo.setBriefDesc("Нямка");
                productInfo.setRating(3.5f);
                productInfo.setUrl("https://ru.wikipedia.org/wiki/Гиннесс");
                ArrayList<Comment>comments = new ArrayList<>(3);
                comments.add(new Comment("Круть", null));
                comments.add(new Comment("Буль-Буль", null));
                comments.add(new Comment("Где я Оо", null));
                productInfo.setComments(comments);
                break;
            case 3:
                productInfo.setName("Жигуль!");
                productInfo.setBriefDesc("Дешево и сердито");
                productInfo.setRating(5.0f);
                productInfo.setUrl("https://ru.wikipedia.org/wiki/Жигулёвское");
                break;
        }
        return productInfo;
    }

    @Override
    public void setOnProductReceivedListener(OnProductReceivedListener listener) {
        this.listener = listener;
    }
}
