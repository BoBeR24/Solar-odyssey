package com.mygdx.game;

//a class that can plot a path from any point to any celestial object, or a path to get in orbit of a celestial body.
public class Pathfinding {

    private static final int MAXFORCE = 3*(10^7);    //The maximum amount of force in Newton the spacecraft is capable of producing.
    private static final int MAXACCELERATIONPOSSIBLE = 6; //The maximum acceleration the spacecraft can produce if m = 5000kg: 6 km/s
    private static final double GRAVITYCONSTANT = 6.67*(10^-11);
    
    //Method that plots a path of a probe to within a given distance of a celestial body, considering a max velocity by wich the probe shousld arrive at the celectial body.
    public static void toBody(Probe probe, celestialBody body, double desiredDestinationVelocity, int desiredDistance){
        Vector directionToBody = body.getLocation().subtract((probe.getLocation()));
        double distanceToGoal = Math.abs(directionToBody.magnitude()) - (desiredDistance + body.getRadius());
        double velocity = probe.getNextVelocity().magnitude();

        double brakingzone = (desiredDistance*desiredDistance - velocity*velocity) / -2*1.5*MAXACCELERATIONPOSSIBLE;
        double acceleration;
        if (distanceToGoal > brakingzone*PhysicsUtils.STEPSIZE + MAXACCELERATIONPOSSIBLE*PhysicsUtils.STEPSIZE){
            acceleration = MAXACCELERATIONPOSSIBLE;
        } else {
            acceleration = -MAXACCELERATIONPOSSIBLE;
        }

        Vector thrust = directionToBody.multiply(acceleration/distanceToGoal);
        Rocketry.thrust(probe, thrust);
    }

    //Method that plots a path of a probe, so that it gets in orbit of the celestial body
    public static void inOrbit(Probe probe, celestialBody body, double desiredDistance){
        double massBody = body.getMass();
        double radius = body.getRadius() + desiredDistance;
        double desiredSpeed = Math.sqrt((GRAVITYCONSTANT*massBody)/radius);
        Vector directionToBody = body.getLocation().subtract((probe.getLocation()));
        double distanceToBodyCenter = Math.abs(directionToBody.magnitude());
            
        double angle = Math.atan(distanceToBodyCenter/radius);
        Vector directionToPointOfStartOrbit = new Vector (directionToBody.x*Math.cos(angle) - directionToBody.y* Math.sin(angle) , directionToBody.x*Math.sin(angle)+directionToBody.y*Math.cos(angle), 0);

        double DistanceToPointOfStartOrbit = Math.sqrt(distanceToBodyCenter*distanceToBodyCenter - radius*radius);
        Vector MoveTodesiredPositionToStartOrbit = new Vector(directionToPointOfStartOrbit.multiply((desiredSpeed - probe.getVelocity().magnitude())/directionToPointOfStartOrbit.magnitude()));
    }
}
