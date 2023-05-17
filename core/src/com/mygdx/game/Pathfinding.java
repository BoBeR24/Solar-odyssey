package com.mygdx.game;

//a class that can plot a path from any point to any celestial object, or a path to get in orbit of a celestial body.
public class Pathfinding {

    private static final int maxForce = 3*(10^7);    //The maximum amount of force in Newton the spacecraft is capable of producing.
    private static final int maxAccelerationPossible = 6*(10^3); //The maximum acceleration the spacecraft can produce if m = 5000kg.
    
    //Method that plots a path of a probe to within a given distance of a celestial body, considering a max velocity by wich the probe should arrive at the celectial body.
    public static void toBody(Probe probe, celestialBody body, int maxVelocity, int distanceToObject){
        Vector directionToBody = body.getLocation().add((probe.getLocation().multiply(-1)));
        double distanceToBody = Math.abs(directionToBody.magnitude());
        double velocity = probe.getVelocity().magnitude();
        
        // figure out if speed adjustment is needed
        double maxAcceleration = 0;
        double maxVelocityPotential = probe.getVelocity().magnitude() + maxAccelerationPossible;
        if(maxAccelerationPossible <= distanceToBody*maxVelocity){
            maxAcceleration = maxVelocityPotential;
        } else {
            maxAcceleration = maxAccelerationPossible/distanceToBody;
        }
    }

    //Method that plots a path of a probe, so that it gets in orbit of the celestial body
    public static void inOrbit(Probe probe, celestialBody body){

    }
}
