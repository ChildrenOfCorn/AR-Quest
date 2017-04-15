package com.vuforia.samples.ar.data.info;

import com.vuforia.samples.ar.data.models.ProductInfo;

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
                productInfo.setUrl("https://ru.wikipedia.org/wiki/Кока-кола");
                break;
            case 2:
                productInfo.setName("Гиннес");
                productInfo.setUrl("https://ru.wikipedia.org/wiki/Гиннесс");
                break;
            case 3:
                productInfo.setName("Жигуль!");
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
