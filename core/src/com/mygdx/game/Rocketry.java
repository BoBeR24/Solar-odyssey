package com.mygdx.game;

// class that gives a probe object rocket functionallity
public class Rocketry {

    private final int maxForce = 3*10^7;    //The maximum amount of force in Newton the spacecraft is capable of producing.
    private final int maxAcceleration = 6*10^3; //The maximum acceleration the spacecraft can produce if m = 5000kg.

    // Method to make a probe go brrr
    public static void thrust(Probe probe, Vector thrust){
        probe.setVelocity(probe.getVelocity().add(thrust));

    }
}
