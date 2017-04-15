package com.vuforia.samples.SampleApplication.models;

import com.vuforia.samples.SampleApplication.utils.MeshObject;

/**
 * Created by grishberg on 15.04.17.
 */
import java.nio.Buffer;

public class Panel extends MeshObject {
    private static final double SIZE = 1.0D;
    // planeTexcoords tekrar edilecek-döşenecek texture sayısı:
    // bir dikdörtgen gibi düşünürsek, koordinatlar
    // şu şekilde x1, y1, x2, y1, x2, y2, x1, y2:
    private final static double planeTexcoords[] =
            {
                    0, 0,
                    SIZE, 0,
                    SIZE, SIZE,
                    0, SIZE
            };

    // Genel texture'un yerleştirileceği piksel cinsinden alan:
    // bir dikdörtgen gibi düşünürsek, koordinatlar
    // şu şekilde x1, y1, x2, y1, x2, y2, x1, y2:
    private final static double planeVertices[] =
            {
                    -100f, -100f, 0.0f,
                    100f, -100f, 0.0f,
                    100f, 100f, 0.0f,
                    -100f, 100f, 0.0f
            };

    private final static double planeNormals[] =
            {
                    0, 0, 1,
                    0, 0, 1,
                    0, 0, 1,
                    0, 0, 1
            };

    private final static short planeIndices[] =
            {
                    0, 1, 2, 0, 2, 3
            };


    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    public Panel() {

        mVertBuff = fillBuffer(planeVertices);
        mTexCoordBuff = fillBuffer(planeTexcoords);
        mNormBuff = fillBuffer(planeNormals);
        mIndBuff = fillBuffer(planeIndices);
    }

    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType) {
        Buffer result = null;
        switch (bufferType) {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
            default:
                break;
        }
        return result;
    }

    @Override
    public int getNumObjectVertex() {
        return planeVertices.length / 3;
    }

    @Override
    public int getNumObjectIndex() {
        return planeIndices.length;
    }

}
