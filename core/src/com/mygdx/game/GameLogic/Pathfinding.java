package com.mygdx.game.GameLogic;

import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Objects.celestialBody;

//a class that can plot a path from any point to any celestial object, or a path to get in orbit of a celestial body.
public class Pathfinding {

    private static final int MAXFORCE = 3 * (10 ^ 7);    //The maximum amount of force in Newton the spacecraft is capable of producing.
    private static final double MAXACCELERATIONPOSSIBLE = 0.6; //The maximum acceleration the spacecraft can produce if m = 5000kg: 6 km/s
    private static final double GRAVITYCONSTANT = 6.67 * (Math.pow(10, -11)) * 100;
    private static final double PI = Math.PI;

    //Method that plots a path of a probe to within a given distance of a celestial body, considering a max velocity by wich the probe shousld arrive at the celectial body.
    public static void toBody(Probe probe, celestialBody body, double pointToSlowDown) {

        Vector velocity = probe.getNextVelocity();
        Vector directionToBody = body.getLocation().subtract(probe.getLocation());
        double distanceToBody = directionToBody.magnitude();
        double maxSpeed = 30000;
        Vector desiredNewVelocity = directionToBody.multiply(maxSpeed / distanceToBody);

        double accelerationX = desiredNewVelocity.x - velocity.x;
        double accelerationY = desiredNewVelocity.y - velocity.y;
        double accelerationZ = desiredNewVelocity.z - velocity.z;

        Vector acceleration = new Vector(accelerationX, accelerationY, accelerationZ);

        if (acceleration.magnitude() > MAXACCELERATIONPOSSIBLE) {
            acceleration.multiply(MAXACCELERATIONPOSSIBLE / acceleration.magnitude());
        }
        Rocketry.thrust(probe, acceleration);
    }

    //Method that plots a path of a probe, so that it gets in orbit of the celestial body
    public static void inOrbit(Probe probe, celestialBody body) {
        Vector velocity = probe.getVelocity();
        Vector directionToBody = body.getLocation().subtract((probe.getLocation()));
        double distanceToBody = directionToBody.magnitude();
        double massBody = body.getMass();
        double OrbitSpeed = Math.sqrt((GRAVITYCONSTANT * massBody) / distanceToBody);
        double desiredX = directionToBody.x * Math.cos(0.5 * PI) - directionToBody.y * Math.sin(0.5 * PI);
        double desiredY = directionToBody.x * Math.sin(0.5 * PI) + directionToBody.y * Math.cos(0.5 * PI);
        Vector desiredDirection = new Vector(desiredX, desiredY, directionToBody.z);

        Vector desiredNewVelocity = desiredDirection.multiply(OrbitSpeed / desiredDirection.magnitude());

        double accelerationX = desiredNewVelocity.x - velocity.x;
        double accelerationY = desiredNewVelocity.y - velocity.y;

        Vector acceleration = new Vector(accelerationX, accelerationY, 0);

        if (acceleration.magnitude() > MAXACCELERATIONPOSSIBLE) {
            acceleration.multiply(MAXACCELERATIONPOSSIBLE / acceleration.magnitude());
        }
        Rocketry.thrust(probe, acceleration);
    }
}
