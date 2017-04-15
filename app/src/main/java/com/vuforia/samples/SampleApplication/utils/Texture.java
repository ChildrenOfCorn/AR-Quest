/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.vuforia.samples.SampleApplication.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

// Support class for the Vuforia samples applications.
// Exposes functionality for loading a texture from the APK.
public class Texture
{
    private static final String LOGTAG = "Vuforia_Texture";
    
    public int mWidth;          // The width of the texture.
    public int mHeight;         // The height of the texture.
    public int mChannels;       // The number of channels.
    public ByteBuffer mData;    // The pixel data.
    public int[] mTextureID = new int[1];
    public boolean mSuccess = false;

    boolean mReady = true;

    final static int CHROMA_KEY = -18751;
    static byte CHROMA_ARRAY[] = {-1, -74, -63, -1};
    static final int CHROMA_DOT = 9445;
    static final int CHROMA_THRESHOLD = 1400;

    /* Factory function to load a texture from the APK. */
    public static Texture loadTextureFromApk(String fileName,
        AssetManager assets)
    {
        InputStream inputStream = null;
        try
        {
            inputStream = assets.open(fileName, AssetManager.ACCESS_BUFFER);
            
            BufferedInputStream bufferedStream = new BufferedInputStream(
                inputStream);
            Bitmap bitMap = BitmapFactory.decodeStream(bufferedStream);
            
            int[] data = new int[bitMap.getWidth() * bitMap.getHeight()];
            bitMap.getPixels(data, 0, bitMap.getWidth(), 0, 0,
                bitMap.getWidth(), bitMap.getHeight());
            
            return loadTextureFromIntBuffer(data, bitMap.getWidth(),
                bitMap.getHeight());
        } catch (IOException e)
        {
            Log.e(LOGTAG, "Failed to log texture '" + fileName + "' from APK");
            Log.i(LOGTAG, e.getMessage());
            return null;
        }
    }
    
    public static int dot(byte one[], byte two[], int from) {
        return one[from] * two[0] +one[from +1]*two[1]
            +one[from+2]*two[2] + one[from+3]*two[3];
    }

    public static Texture loadTextureFromIntBuffer(int[] data, int width,
        int height)
    {
        // Convert:
        int numPixels = width * height;
        byte[] dataBytes = new byte[numPixels * 4];
        
        for (int p = 0; p < numPixels; ++p)
        {
            int colour = data[p];
            dataBytes[p * 4] = (byte) (colour >>> 16); // R
            dataBytes[p * 4 + 1] = (byte) (colour >>> 8); // G
            dataBytes[p * 4 + 2] = (byte) colour; // B
            dataBytes[p * 4 + 3] = (byte) (colour >>> 24);
            int delta = Math.abs(dot(dataBytes, CHROMA_ARRAY, p * 4) - CHROMA_DOT);
            if (delta < CHROMA_THRESHOLD) {
                dataBytes[p*4] = 0;
                dataBytes[p*4 + 1] = 0;
                dataBytes[p*4 + 2] = 0;
                dataBytes[p*4 + 3] = (byte) 200;

            }
        }
        
        Texture texture = new Texture();
        texture.mWidth = width;
        texture.mHeight = height;
        texture.mChannels = 4;
        
        texture.mData = ByteBuffer.allocateDirect(dataBytes.length).order(
            ByteOrder.nativeOrder());
        int rowSize = texture.mWidth * texture.mChannels;
        for (int r = 0; r < texture.mHeight; r++)
            texture.mData.put(dataBytes, rowSize * (texture.mHeight - 1 - r),
                rowSize);
        
        texture.mData.rewind();
        
        // Cleans variables
        dataBytes = null;
        data = null;
        
        texture.mSuccess = true;
        return texture;
    }


    public static Texture loadTextureFromBitmap(Bitmap bitMap){
        int[] data = new int[bitMap.getWidth() * bitMap.getHeight()];
        bitMap.getPixels(data, 0, bitMap.getWidth(), 0, 0,
                bitMap.getWidth(), bitMap.getHeight());

        Texture texture =  loadTextureFromIntBuffer(data, bitMap.getWidth(),
                bitMap.getHeight());
        texture.mReady = false;
        return texture;

    }

    public static Texture loadTextureFromARGBBitmap(Bitmap bitMap) {

        int[] data = new int[bitMap.getWidth() * bitMap.getHeight()];
        for ( int y = 0; y < bitMap.getHeight(); y++ )
            for ( int x = 0; x < bitMap.getWidth(); x++ )
            {
                int pixel = bitMap.getPixel(x, y);
                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8 ) & 0xFF;
                int blue = (pixel) & 0xFF;
                data[y*bitMap.getWidth() + x]=(red<<24 |green << 16 | blue << 8 | alpha);
            }
        Texture texture = loadTextureFromIntBuffer(data, bitMap.getWidth(),
                                        bitMap.getHeight());
        texture.mReady = false;
        return texture;
    }

    public void setReady(final boolean ready) {
        this.mReady = ready;
    }

    public boolean isReady() {
        return mReady;
    }
}
