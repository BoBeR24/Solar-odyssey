package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;

import java.util.ArrayList;

/**
 * Function for calculating Newtons Law of Gravity
 * */
public class NewtonForce implements Function{
    /** Calculates sum of the forces
     * */
    public Vector allForces(Body body, ArrayList<Body> universe) {
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

    /**Calculates the force exerted from the planet on the body
     * @param body body to which force is applied
     * @param planet body from which gravitational effect is considered
     * @return force vector
     */
    public Vector singleForce(Body body, Body planet){
        Vector force = new Vector(0.0, 0.0, 0.0);

        double scalingFactor = PhysicsUtils.gravitationalConstant * planet.getMass() * body.getMass() * (-1);
        Vector planetVector = planet.getLocation();
        Vector bodyVector = body.getLocation();

        //Difference between the vectors of the two celestial bodies
        force = bodyVector.subtract(planetVector);
        double magnitude = Math.pow(force.magnitude(), 3);
        force = force.multiply(scalingFactor / magnitude);

        return force;
    }

    /** Calculates change in coordinates at the given time
     * @see Function#calculateCoordinateChange(Body, ArrayList, float)
     * */
    @Override
    public Vector calculateCoordinateChange(Body body, ArrayList<Body> universe, float startTime){
        return new Vector(
                (body.getVelocity().x),
                (body.getVelocity().y),
                (body.getVelocity().z)
        );
    }

    /** Calculates change in velocity at the given time
     * @see Function#calculateVelocityChange(Body, ArrayList, float)
     * */
    @Override
    public Vector calculateVelocityChange(Body body, ArrayList<Body> universe, float startTime){
        Vector funcVal = allForces(body, universe);

        return new Vector(
                (funcVal.x) / body.getMass(),
                (funcVal.y) / body.getMass(),
                (funcVal.z) / body.getMass()
        );
    }
}
