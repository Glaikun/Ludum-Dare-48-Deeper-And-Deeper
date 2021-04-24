package com.glaikunt.framework.esc.component.misc;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;

public class FadeComponent implements Component {

    private final List<Fade> fades = new ArrayList<>();

    public void addFade(Fade fade) {
        this.fades.add(fade);
    }

    public List<Fade> getFades() {
        return fades;
    }

    public static class Fade {
        private boolean fadeIn, fadeOut;
        private float fade, maxFade;

        public void setFade(float fade) {
            this.fade = fade;
        }

        public float getFade() {
            return fade;
        }

        public void setFadeIn(boolean fadeIn) {
            if (isFadeOut() && fadeIn) {
                setFadeOut(false);
            }
            this.fadeIn = fadeIn;
        }

        public boolean isFadeIn() {
            return fadeIn;
        }

        public void setFadeOut(boolean fadeOut) {
            if (isFadeIn() && fadeOut) {
                setFadeIn(false);
            }
            this.fadeOut = fadeOut;
        }

        public boolean isFadeOut() {
            return fadeOut;
        }

        public void setMaxFade(float maxFade) {
            this.maxFade = maxFade;
        }

        public float getMaxFade() {
            return maxFade;
        }
    }
}
