package com.vuforia.samples.ar.data.info;

import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.support.annotation.Nullable;

import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.models.ProductInfo;

import lombok.NonNull;

/**
 * Created by grishberg on 15.04.17.
 */

public class StubInfoTextureBuilder implements InfoTextureBuilder {
    private final AssetManager assetManager;

    @Nullable
    private OnTextureBuildListener onTextureBuildListener;

    public StubInfoTextureBuilder(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void getTextureBitmapFromInfo(@NonNull ProductInfo productInfo) {
        Texture texture = null;
        switch ((int) productInfo.getId()) {
            case 1:
                texture = Texture.loadTextureFromApk("TextureTeapotBrass.png", assetManager);
                break;
            case 2:
                texture = Texture.loadTextureFromApk("TextureTeapotBlue.png", assetManager);
                break;
            case 3:
                texture = Texture.loadTextureFromApk("TextureTeapotRed.png", assetManager);
                break;
        }
        initTexture(texture);
        if (onTextureBuildListener != null) {
            onTextureBuildListener.onTextureReady(texture);
        }
    }

    @Override
    public void setTextureBuildListener(OnTextureBuildListener onTextureBuildListener) {
        this.onTextureBuildListener = onTextureBuildListener;
    }

    private void initTexture(Texture texture) {
        GLES20.glGenTextures(1, texture.mTextureID, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.mTextureID[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA,
                texture.mWidth, texture.mHeight, 0, GLES20.GL_RGBA,
                GLES20.GL_UNSIGNED_BYTE, texture.mData);
    }
}
