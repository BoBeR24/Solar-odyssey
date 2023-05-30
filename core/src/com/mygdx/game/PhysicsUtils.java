package com.mygdx.game;

import java.util.ArrayList;

/**
 * class with methods for calculating and updating physical processes of bodies presented in the system
 * */
public class PhysicsUtils{
    //Gravitational constant expressed in cubic kilometers per kilogram per second squared
    private final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 30;

   /**
    * initializes methods for updating velocity and coordinates
    * @param body object that the forces are being exerted on
    */
   // one of the ways may be to move for loop for all planets to solvers themselves,
   // so each call of solver would calculate next state for all bodies
    public static void calculateNextState(Body body){

        Vector[] newValues = EulerSolver.solve(body, STEPSIZE);

        body.setNextLocation(newValues[0].x, newValues[0].y, newValues[0].z);
        body.setNextVelocity(newValues[1].x, newValues[1].y, newValues[1].z);

    }

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
}
