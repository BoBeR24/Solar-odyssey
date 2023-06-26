package com.mygdx.game.GUI;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Clock {
    private LocalDateTime date;
    private int hours;
    private int minutes;
    private int seconds;
    private int totalSeconds;
    private int daysPased;
    /**
     * constructor for Clock class taking date you want to start from satring with time 00:00
     * @param day 
     * @param month
     * @param year
     */
    public Clock(int day, int month, int year) {
        LocalDateTime startingDay = LocalDateTime.of(year, month, day, 0, 0, 0);
        this.date = startingDay;
        this.hours = 0;
        this.minutes =0;
        this.seconds = 0;
        this.totalSeconds = 0;
        this.daysPased = 0;
    }
    /**
     * Method updating all of the instances of CLock with time passed in seconds
     * @param timeStep
     */
    public void updateTime(int timeStep) {
        totalSeconds += timeStep;
        daysPased = (int)(totalSeconds/3600)/24;
        this.date = this.date.plus(timeStep, ChronoUnit.SECONDS);

        hours = (int) ((totalSeconds / 3600) % 24);
        minutes = (int) ((totalSeconds / 60) % 60);
        seconds = (int) (totalSeconds % 60);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int totalSeconds() {
        return totalSeconds;
    }

    public int getDaysPassed(){
        return daysPased;
    }

}
