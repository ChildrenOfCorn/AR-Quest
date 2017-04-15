package com.vuforia.samples.ar.data.info;

import android.content.res.AssetManager;

import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.models.ProductInfo;

/**
 * Created by grishberg on 15.04.17.
 */

public class StubInfoTextureBuilder implements InfoTextureBuilder {
    private final AssetManager assetManager;

    public StubInfoTextureBuilder(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Texture getTextureBitmapFromInfo(ProductInfo productInfo) {
        switch (productInfo.getId()) {
            case 1:
                return Texture.loadTextureFromApk("TextureTeapotBrass.png", assetManager);
            case 2:
                return Texture.loadTextureFromApk("TextureTeapotBlue.png", assetManager);
            case 3:
                return Texture.loadTextureFromApk("TextureTeapotRed.png", assetManager);
        }
        return null;
    }
}
