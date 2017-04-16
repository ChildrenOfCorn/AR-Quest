/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.vuforia.samples.VuforiaSamples.app.ImageTargets;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vuforia.Device;
import com.vuforia.Matrix44F;
import com.vuforia.State;
import com.vuforia.Tool;
import com.vuforia.Trackable;
import com.vuforia.TrackableResult;
import com.vuforia.Vuforia;
import com.vuforia.samples.SampleApplication.SampleAppRenderer;
import com.vuforia.samples.SampleApplication.SampleAppRendererControl;
import com.vuforia.samples.SampleApplication.SampleApplicationSession;
import com.vuforia.samples.SampleApplication.models.Panel;
import com.vuforia.samples.SampleApplication.utils.CubeShaders;
import com.vuforia.samples.SampleApplication.utils.LoadingDialogHandler;
import com.vuforia.samples.SampleApplication.utils.SampleApplication3DModel;
import com.vuforia.samples.SampleApplication.utils.SampleUtils;
import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.info.InfoTextureBuilder;
import com.vuforia.samples.ar.data.info.ProductInfoInteractor;
import com.vuforia.samples.ar.data.beans.ObjectInfo;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.di.DiContainer;

import lombok.Getter;

// The renderer class for the ImageTargets sample.
public class ImageTargetRenderer implements GLSurfaceView.Renderer, SampleAppRendererControl,
        InfoTextureBuilder.OnTextureBuildListener, ProductInfoInteractor.OnProductReceivedListener {
    private static final String TAG = "ImageTargetRenderer";
    private static final float PANEL_RIGHT_OFFSET = 0.2f;

    private ProductInfoInteractor productInfoInteractor;
    private InfoTextureBuilder infoTextureBuilder;
    private Texture prevTexture;

    private SampleApplicationSession vuforiaAppSession;
    private ImageTargets mActivity;
    private SampleAppRenderer mSampleAppRenderer;

    private int shaderProgramID;
    private int vertexHandle;
    private int textureCoordHandle;
    private int mvpMatrixHandle;
    private int texSampler2DHandle;

    private Panel panel;
    private long lastTargetId = -1;

    @Getter
    private ProductInfo currentProductInfo;
    private ProductInfo lastProductInfo;

    private float kBuildingScale = 0.012f;
    private SampleApplication3DModel mBuildingsModel;

    private boolean mIsActive = false;
    private boolean mModelIsLoaded = false;

    // Масштабирование объектов на сцене
    private static final float OBJECT_SCALE_FLOAT = 0.001f;

    public ImageTargetRenderer(ImageTargets activity, SampleApplicationSession session) {
        productInfoInteractor = DiContainer.provideProductInfoInteractor();
        productInfoInteractor.setOnProductReceivedListener(this);
        infoTextureBuilder = DiContainer.provideInfoTextureBuilder();
        infoTextureBuilder.setTextureBuildListener(this);

        mActivity = activity;
        vuforiaAppSession = session;
        // SampleAppRenderer used to encapsulate the use of RenderingPrimitives setting
        // the device mode AR/VR and stereo mode
        mSampleAppRenderer = new SampleAppRenderer(this, mActivity, Device.MODE.MODE_AR, false, 0.01f, 5f);
    }

    // Called to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        if (!mIsActive)
            return;

        // Call our function to render content from SampleAppRenderer class
        mSampleAppRenderer.render();
    }

    public void setActive(boolean active) {
        mIsActive = active;

        if (mIsActive)
            mSampleAppRenderer.configureVideoBackground();
    }

    // Called when the surface is created or recreated.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "GLRenderer.onSurfaceCreated");

        // Call Vuforia function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        vuforiaAppSession.onSurfaceCreated();

        mSampleAppRenderer.onSurfaceCreated();
    }

    // Called when the surface changed size.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "GLRenderer.onSurfaceChanged");

        // Call Vuforia function to handle render surface size changes:
        vuforiaAppSession.onSurfaceChanged(width, height);

        // RenderingPrimitives to be updated when some rendering change is done
        mSampleAppRenderer.onConfigurationChanged(mIsActive);

        initRendering();
    }

    // Function for initializing the renderer.
    private void initRendering() {
        Log.d(TAG, "initRendering: ");
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, Vuforia.requiresAlpha() ? 0.0f
                : 1.0f);

        shaderProgramID = SampleUtils.createProgramFromShaderSrc(
                CubeShaders.CUBE_MESH_VERTEX_SHADER,
                CubeShaders.CUBE_MESH_FRAGMENT_SHADER);

        vertexHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexPosition");
        textureCoordHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgramID,
                "modelViewProjectionMatrix");
        texSampler2DHandle = GLES20.glGetUniformLocation(shaderProgramID,
                "texSampler2D");

        if (!mModelIsLoaded) {
            panel = new Panel();

            try {
                mBuildingsModel = new SampleApplication3DModel();
                mBuildingsModel.loadModel(mActivity.getResources().getAssets(),
                        "ImageTargets/Buildings.txt");
                mModelIsLoaded = true;
            } catch (IOException e) {
                Log.e(TAG, "Unable to load buildings");
            }

            // Hide the Loading Dialog
            mActivity.loadingDialogHandler
                    .sendEmptyMessage(LoadingDialogHandler.HIDE_LOADING_DIALOG);
        }
    }

    public void updateConfiguration() {
        mSampleAppRenderer.onConfigurationChanged(mIsActive);
    }

    // The render function called from SampleAppRendering by using RenderingPrimitives views.
    // The state is owned by SampleAppRenderer which is controlling it's lifecycle.
    // State should not be cached outside this method.
    public void renderFrame(State state, float[] projectionMatrix) {
        // Renders video background replacing Renderer.DrawVideoBackground()
        mSampleAppRenderer.renderVideoBackground();

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // handle face culling, we need to detect if we are using reflection
        // to determine the direction of the culling
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);

        boolean objectinfoRequested = false;

        // Did we find any trackables this frame?
        for (int tIdx = 0; tIdx < state.getNumTrackableResults(); tIdx++) {
            objectinfoRequested = true;
            TrackableResult result = state.getTrackableResult(tIdx);
            Trackable trackable = result.getTrackable();

            ObjectInfo userData = (ObjectInfo) trackable.getUserData();
            getTextureByObjectInfoIfRequired(userData);

            if (prevTexture == null) {
                continue;
            }
            if (!prevTexture.isReady()) {
                initTexture(prevTexture);
            }

            Matrix44F modelViewMatrix_Vuforia = Tool
                    .convertPose2GLMatrix(result.getPose());
            float[] modelViewMatrix = modelViewMatrix_Vuforia.getData();

            // deal with the modelview and projection matrices
            float[] modelViewProjection = new float[16];

            // поворот объекта относительно центра
            Matrix.translateM(modelViewMatrix, 0, PANEL_RIGHT_OFFSET, 0.0f,
                    OBJECT_SCALE_FLOAT);
            Matrix.scaleM(modelViewMatrix, 0, OBJECT_SCALE_FLOAT,
                    OBJECT_SCALE_FLOAT, OBJECT_SCALE_FLOAT);

            Matrix.multiplyMM(modelViewProjection, 0, projectionMatrix, 0, modelViewMatrix, 0);

            // activate the shader program and bind the vertex/normal/tex coords
            GLES20.glUseProgram(shaderProgramID);

            GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT,
                    false, 0, panel.getVertices());
            GLES20.glVertexAttribPointer(textureCoordHandle, 2,
                    GLES20.GL_FLOAT, false, 0, panel.getTexCoords());

            GLES20.glEnableVertexAttribArray(vertexHandle);
            GLES20.glEnableVertexAttribArray(textureCoordHandle);

            // activate texture 0, bind it, and pass to shader
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, prevTexture.mTextureID[0]);
            GLES20.glUniform1i(texSampler2DHandle, 0);

            // pass the model view matrix to the shader
            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false,
                    modelViewProjection, 0);

            // finally draw the teapot
            GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                    panel.getNumObjectIndex(), GLES20.GL_UNSIGNED_SHORT,
                    panel.getIndices());

            // disable the enabled arrays
            GLES20.glDisableVertexAttribArray(vertexHandle);
            GLES20.glDisableVertexAttribArray(textureCoordHandle);

            SampleUtils.checkGLError("Render Frame");
        }

        if (!objectinfoRequested) {
            lastTargetId = -1;
            currentProductInfo = null;
            prevTexture = null;
        }

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    }

    private void getTextureByObjectInfoIfRequired(ObjectInfo objectInfo) {
        long targetId = objectInfo.getId();

        if (targetId == lastTargetId) {
            currentProductInfo = lastProductInfo;
            return;
        }

        lastTargetId = targetId;

        Log.d(TAG, "Current product info was reset, lastTargetId = " + lastTargetId);
        currentProductInfo = null;
        prevTexture = null;

        productInfoInteractor.getProductInfoByTargetId(targetId);
    }

    private void initTexture(final Texture texture) {
        GLES20.glGenTextures(1, texture.mTextureID, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.mTextureID[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA,
                texture.mWidth, texture.mHeight, 0, GLES20.GL_RGBA,
                GLES20.GL_UNSIGNED_BYTE, texture.mData);
        texture.setReady(true);
    }

    @Override
    public void onProductInfoReceived(@NonNull ProductInfo productInfo) {
        Log.d(TAG, "UserData:Retrieved User Data	\"" + productInfo + "\"");

        this.currentProductInfo = productInfo;
        this.lastProductInfo = productInfo;

        infoTextureBuilder.getTextureBitmapFromInfo(productInfo);
    }

    @Override
    public void onTextureReady(Texture texture) {
        Log.d(TAG, "onTextureReady: texture = " + texture);
        prevTexture = texture;
    }
}
