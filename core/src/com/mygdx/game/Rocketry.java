package com.mygdx.game;

// class that gives a probe object rocket functionallity
public class Rocketry {

    // Method to make a probe go brrr, vector in km/s
    public static void thrust(Probe probe, Vector thrust){
        probe.setNextVelocity(probe.getNextVelocity().add(thrust));
    }
}
