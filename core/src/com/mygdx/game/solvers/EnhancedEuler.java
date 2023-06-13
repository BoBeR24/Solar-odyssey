package com.mygdx.game.solvers;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;

import java.util.ArrayList;

public class EnhancedEuler{
    public static void calculateNextState(ArrayList<Body> universe){
        final int STEPSIZE = PhysicsUtils.STEPSIZE;

        ArrayList<Body> cloned_universe = new ArrayList<>();

        for (Body body: universe) {
            cloned_universe.add(body.clone());
        }

        //Calculate next set of values with default euler solver approach
        EulerSolver.calculateNextState(cloned_universe);
        updateUniverse(cloned_universe);

        for (Body body : universe) {
            //Calculate forces in original universe from current state
            Vector forceOriginal = PhysicsUtils.allForce(body, SolarSystem.bodies);
            //Calculate forces for the same body, but in cloned universe(which is in the next state, approximated by
            // default solver)
            Vector forceClone = PhysicsUtils.allForce(cloned_universe.get(body.getId()), cloned_universe);

            //Average the force vector previously calculated
            Vector forceSum = (forceClone.add(forceOriginal)).multiply(0.5);

            //Calculate velocity and position with old body and new average force vector and return it
            body.setNextLocation(updateCoordinate(body, STEPSIZE));
            body.setNextVelocity(updateVelocity(body, forceSum, STEPSIZE));
        }
    }   

    /** update coordinates of the body
     * */
    private static Vector updateCoordinate(Body body, int STEPSIZE){
        return new Vector(
        (body.getLocation().x + body.getVelocity().x * STEPSIZE),
        (body.getLocation().y + body.getVelocity().y * STEPSIZE),
        (body.getLocation().z + body.getVelocity().z * STEPSIZE));
    }

    /** update velocity of the body
     * */
    private static Vector updateVelocity(Body body, Vector forcesSum, int STEPSIZE){
        return new Vector(
        body.getVelocity().x + (forcesSum.x * STEPSIZE) / body.getMass(),
        body.getVelocity().y + (forcesSum.y * STEPSIZE) / body.getMass(),
        body.getVelocity().z + (forcesSum.z * STEPSIZE) / body.getMass());
    }

    /** Updates whole given universe(each body from it)
     * */
    private static void updateUniverse(ArrayList<Body> universeToUpdate){
        for (Body body : universeToUpdate) {
            body.update();
        }
    }
}
