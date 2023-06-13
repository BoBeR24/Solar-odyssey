package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;

import java.util.ArrayList;

/**
 * class with methods for calculating and updating physical processes of bodies presented in the system
 * */
public class PhysicsUtils{
    //Gravitational constant expressed in cubic kilometers per kilogram per second squared
    private final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 1;
    //Nasa Coordinates after 1 year
    private final static Vector NASA_Earth = new Vector(-146525538.264264, -29597910.637783, 2927.15182555466);

    /** Calculates sum of the forces
     * */
    public static Vector allForce(Body body, ArrayList<Body> universe) {
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        // Loops through all celestial bodies except itself and the probe since an object cant affect itself
        for (Body planet : universe){
            if (planet.getId() == body.getId() || planet.getId() == SystemProperties.PROBE) {
                continue;
            }

            forcesSum = forcesSum.add(singleForce(body, planet));
        }

        return forcesSum;
    }

    //Calculates the force exerted from the planet on the body
    public static Vector singleForce(Body body, Body planet){
        Vector force = new Vector(0.0, 0.0, 0.0);

        double scalingFactor = gravitationalConstant * planet.getMass() * body.getMass() * (-1);
        Vector planetVector = planet.getLocation();
        Vector bodyVector = body.getLocation();

        //Difference between the vectors of the two celestial bodies
        force = bodyVector.subtract(planetVector);
        double magnitude = Math.pow(force.magnitude(), 3);
        force = force.multiply(scalingFactor / magnitude);

        return force;
    }

    public static double relativeError(Vector Location){
        Vector Error = Location.subtract(NASA_Earth);
        Error.x = Math.abs(Error.x * (1.0/ NASA_Earth.x));
        Error.y = Math.abs(Error.y * (1.0/ NASA_Earth.y));
        Error.z = Math.abs(Error.z * (1.0/NASA_Earth.z));

        Error.multiply(100);

        double averageError = (Error.x + Error.y + Error.z)/3;
        return averageError;
    }
}
