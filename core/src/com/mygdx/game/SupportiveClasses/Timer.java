package com.mygdx.game.SupportiveClasses;

/**
 * class to keep track of time with each iteration
 */
public class Timer {
    private final float endTime;
    public float stepSize;
    private float currentTime = 0;
    private boolean timeReached = false;
    private boolean isPaused = false;

    public Timer(float endTime, float stepSize) {
        this.endTime = endTime;
        this.stepSize = stepSize;
    }

    /**
     * increases timer by the step size. Can't iterate if timer is set to pause. Can't iterate further then endTime
     */
    public void iterate() {
        // if timer is paused it can't iterate
        if (isPaused) {
            return;
        }

        if (currentTime >= endTime) {
            timeReached = true;
            return;
        }

        currentTime += this.stepSize;
    }

    /**
     * get amount of time passed since start of the timer
     */
    public float getTimePassed() {
        return currentTime;
    }

    /**
     * check whether specified end time was reached or not
     */
    public boolean isTimeReached() {
        return timeReached;
    }

    /**
     * stop timer
     */
    public void pause() {
        isPaused = true;
    }

    /**
     * continue timer
     */
    public void unpause() {
        isPaused = false;
    }
}
