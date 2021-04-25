package com.glaikunt.framework.application;

import com.badlogic.gdx.audio.Sound;
import com.glaikunt.framework.application.cache.SoundCache;

public class AudioManager {

    private float volume = 0;

    private Sound gameMusic;

    private long loopKey;

    public AudioManager() {
    }

    public void init(SoundCache soundCache) {

        gameMusic = soundCache.getSoundCache(SoundCache.MUSIC);
    }

    public void update(float delta) {

    }

    public void loopGameMusic() {

        loopKey = gameMusic.loop(volume * .5f);
    }

    public void stopGameMusic() {

        gameMusic.stop(loopKey);
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }
}
