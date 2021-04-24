package com.glaikunt.framework.esc.component.animation;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {

    private IntMap<Animation<TextureRegion>> entries = new IntMap<>();
    private int currentAnimationId = 0;
    private TextureRegion currentFrame;
    private float stateTime, framerate;
    private boolean playing;
    private boolean xFlip, yFlip;

    public AnimationComponent(AnimationComponent animationComponent) {

        this.entries = animationComponent.entries;
        this.currentAnimationId = animationComponent.currentAnimationId;
        this.currentFrame = animationComponent.currentFrame;
        this.stateTime = animationComponent.stateTime;
        this.framerate = animationComponent.framerate;
        this.playing = animationComponent.playing;
    }

    public AnimationComponent(Texture texture, int cols, int rows, boolean flipx, boolean flipy) {

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / cols,
                texture.getHeight() / rows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
                fixBleeding(tmp[i][j]);
                tmp[i][j].flip(flipx, flipy);
            }
        }
        framerate = 0.095f;

        // Initialize the Animation with the frame interval and array of frames
        Animation<TextureRegion> animation = new Animation<>(framerate, walkFrames);
        entries.put(0, animation);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        setPlaying(true);
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public AnimationComponent(Texture texture, int cols, int rows) {
        this(texture, cols, rows, false, false);
    }

    public AnimationComponent(IntMap<Texture> textures, int startFrame, int cols, int rows) {
        setup(textures, startFrame, cols, rows);
    }

    public AnimationComponent() {

    }

    public void setup(IntMap<Texture> textures, int startFrame, int cols, int rows) {
        for (IntMap.Entry<Texture> texture : textures) {
            // Use the split utility method to create a 2D array of TextureRegions. This is
            // possible because this sprite sheet contains frames of equal size and they are
            // all aligned.
            TextureRegion[][] tmp = TextureRegion.split(texture.value,
                    texture.value.getWidth() / cols,
                    texture.value.getHeight() / rows);

            // Place the regions into a 1D array in the correct order, starting from the top
            // left, going across first. The Animation constructor requires a 1D array.
            TextureRegion[] walkFrames = new TextureRegion[cols * rows];
            int index = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    walkFrames[index++] = tmp[i][j];
                    fixBleeding(tmp[i][j]);
                }
            }
            framerate = 0.095f;

            // Initialize the Animation with the frame interval and array of frames
            Animation<TextureRegion> animation = new Animation<>(framerate, walkFrames);
            entries.put(texture.key, animation);
            animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }

        currentAnimationId = startFrame;
        setPlaying(true);
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public static void fixBleeding(TextureRegion region) {
        float fix = 0.01f;

        float x = region.getRegionX();
        float y = region.getRegionY();
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float invTexWidth = 1f / region.getTexture().getWidth();
        float invTexHeight = 1f / region.getTexture().getHeight();
        region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getCurrentFrameIndex() {
        return getCurrentAnimation().getKeyFrameIndex(getStateTime());
    }

    public Animation<TextureRegion> getCurrentAnimation() {
        return entries.get(currentAnimationId);
    }

    public Animation.PlayMode getPlayMode() {
        return getCurrentAnimation().getPlayMode();
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        setStateTime(0);
        this.getCurrentAnimation().setPlayMode(playMode);
    }

    public boolean isAnimationFinished() {
        return entries.get(currentAnimationId).isAnimationFinished(getStateTime());
    }

    public float getFramerate() {
        return framerate;
    }

    public void setFramerate(float framerate) {
        for (Animation<TextureRegion> animation : entries.values()) {
            animation.setFrameDuration(framerate);
        }
        this.framerate = framerate;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public IntMap<Animation<TextureRegion>> getEntries() {
        return entries;
    }

    public void setEntries(IntMap<Animation<TextureRegion>> entries) {
        this.entries = entries;
    }

    public int getCurrentAnimationId() {
        return currentAnimationId;
    }

    public void setCurrentAnimationId(int currentAnimationId) {
        this.currentAnimationId = currentAnimationId;
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public void reset() {
        setStateTime(0);
        setCurrentFrame(getCurrentAnimation().getKeyFrame(getStateTime()));
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public TextureRegion getLastFrame() {
        return getCurrentAnimation().getKeyFrame(getCurrentAnimation().getAnimationDuration());
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean isxFlip() {
        return xFlip;
    }

    public void setxFlip(boolean xFlip) {
        this.xFlip = xFlip;
    }

    public boolean isyFlip() {
        return yFlip;
    }

    public void setyFlip(boolean yFlip) {
        this.yFlip = yFlip;
    }
}
