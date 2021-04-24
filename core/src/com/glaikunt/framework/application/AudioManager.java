package com.glaikunt.framework.application;

import com.glaikunt.framework.application.cache.SoundCache;

public class AudioManager {

    private float volume = 1;

    public AudioManager() {
    }

    public void init(SoundCache soundCache) {
    }

    public void update(float delta) {

    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }
}
