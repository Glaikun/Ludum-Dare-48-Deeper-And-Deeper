package com.glaikunt.framework.application;

public class TickTimer {

    private float tick = 0;
    private float targetTime;

    public TickTimer(float targetTime) {
        this.targetTime = targetTime;
    }

    public void tick(float delta) {

        if (tick < targetTime) {
            tick += (1 * delta);
        } else if (tick != targetTime) {
            tick = targetTime;
        }
    }

    public boolean isTimerEventReady(){
        if(tick >= targetTime){
            resetTick();
            return true;
        }
        return false;
    }

    public boolean isTimerPassedTarget() {
        if(tick >= targetTime){
            return true;
        }
        return false;
    }

    public void resetTick() {
        tick = 0;
    }

    public void setTargetTime(float targetTime) {
        this.targetTime = targetTime;
    }

    public float getTargetTime() {
        return targetTime;
    }

    public void setTick(float tick) {
        this.tick = tick;
    }

    public float getTick() {
        return tick;
    }
}
