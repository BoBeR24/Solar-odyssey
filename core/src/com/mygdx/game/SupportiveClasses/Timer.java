package com.mygdx.game.SupportiveClasses;

/** class to keep track of time with each iteration
 * */
public class Timer {
    private final int endTime;
    private int currentTime;
    private boolean timeReached = false;
    private boolean isPaused = false;

    public Timer(int endTime) {
        this.endTime = endTime;
    }

    /** increases timer by the amount. Can't iterate if timer is set to pause. Can't iterate further then endTime
     * @param amount value by which we increase timer
     * */
    public void iterate(int amount) {
        // if timer is paused it can't iterate
        if (isPaused) {
            return;
        }

        if (currentTime >= endTime) {
            timeReached = true;
            return;
        }

        currentTime += amount;
    }

    /** get amount of time passed since start of the timer
     * */
    public int getTimePassed(){
        return currentTime;
    }

    /** check whether specified end time was reached or not
     * */
    public boolean isTimeReached() {
        return timeReached;
    }

    /** stop timer
     * */
    public void pause() {
        isPaused = true;
    }

    /** continue timer
     * */
    public void unpause() {
        isPaused = false;
    }
}
