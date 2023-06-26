package com.mygdx.game.SupportiveClasses;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * class to keep track of time with each iteration
 */
public class Timer {
    private final float endTime;
    public float stepSize;
    private float currentTime = 0;
    private boolean timeReached = false;
    private boolean isPaused = false;
    private LocalDateTime date;

    /**
     * Set up timer and specify the moment it was created
     * @param endTime when timer should end
     * @param stepSize step with which we should iterate our timer
     * @param year year when timer was created
     * @param month month when timer was created
     * @param day day of month when timer was created
     * */
    public Timer(float endTime, float stepSize, int year, int month, int day) {
        this.endTime = endTime;
        this.stepSize = stepSize;


        date = LocalDateTime.of(year, month, day, 0, 0, 0);
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

        this.date = date.plus((long) this.stepSize, ChronoUnit.SECONDS);
    }

    /**
     * get amount of time passed since start of the timer
     * @return time passed in seconds
     */
    public float getTimePassed() {
        return currentTime;
    }

    /**
     * Get current date in human-readable format
     * */
    public LocalDateTime getDate() {
        return this.date;
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
