package com.vuforia.samples.VuforiaSamples.app.ImageTargets;

import com.vuforia.State;
import com.vuforia.Trackable;
import com.vuforia.TrackableResult;
import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.beans.ObjectInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by grishberg on 16.04.17.
 */

public class TextureManager {
    private final Map<Long, Texture> textureMap = new ConcurrentHashMap<>();

    public void updateOldTextures(State state) {
        Map<Long, Texture> textureBuffer = new HashMap<>(textureMap);

        for (int tIdx = 0; tIdx < state.getNumTrackableResults(); tIdx++) {
            TrackableResult result = state.getTrackableResult(tIdx);
            Trackable trackable = result.getTrackable();
            ObjectInfo objectInfo = (ObjectInfo) trackable.getUserData();
            textureBuffer.remove(objectInfo.getId());
        }
        for (Map.Entry<Long, Texture> entry : textureBuffer.entrySet()) {
            textureMap.remove(entry.getKey());
        }
    }

    public Texture getTexture(ObjectInfo objectInfo) {
        return textureMap.get(objectInfo.getId());
    }

    public void putTexture(long id, Texture result) {
        textureMap.put(id, result);
    }
}
