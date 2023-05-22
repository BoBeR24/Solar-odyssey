package com.mygdx.game;

//a class that can plot a path from any point to any celestial object, or a path to get in orbit of a celestial body.
public class Pathfinding {

    private static final int MAXFORCE = 3*(10^7);    //The maximum amount of force in Newton the spacecraft is capable of producing.
    private static final int MAXACCELERATIONPOSSIBLE = 6; //The maximum acceleration the spacecraft can produce if m = 5000kg: 6 km/s
    
    //Method that plots a path of a probe to within a given distance of a celestial body, considering a max velocity by wich the probe should arrive at the celectial body.
    public static void toBody(Probe probe, celestialBody body, int maxVelocity, int desiredDistance){
        Vector directionToBody = body.getLocation().subtract((probe.getLocation()));
        double distanceToGoal = Math.abs(directionToBody.magnitude()) - desiredDistance;
        double velocity = probe.getNextVelocity().magnitude();
        
        double maxAcceleration;
        if(MAXACCELERATIONPOSSIBLE >= distanceToGoal*maxVelocity){
            maxAcceleration = MAXACCELERATIONPOSSIBLE;
         }else {
            maxAcceleration = (MAXACCELERATIONPOSSIBLE/distanceToGoal);
        }
        Vector thrust = new Vector(directionToBody.multiply((maxAcceleration/distanceToGoal)));
        Rocketry.thrust(probe, thrust);
    }

    //Method that plots a path of a probe, so that it gets in orbit of the celestial body
    public static void inOrbit(Probe probe, celestialBody body, double desiredDistance){

    }
}
