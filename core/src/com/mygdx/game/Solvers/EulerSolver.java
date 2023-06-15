package com.mygdx.game.Solvers;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Objects.Vector;

import java.util.ArrayList;

/** One of several different ODE solver methods based on classic 1st order Euler's method.
 *
 * */
public class EulerSolver implements Solver{
    /** method to initiate solving process.
     * @param universe states for which set of body we want approximate next state
     * */
    public void calculateNextState(ArrayList<Body> universe, Function function, float initialTime){
        for (Body body : universe) {
            final int STEPSIZE = PhysicsUtils.STEPSIZE;

            Vector force = function.evaluate(body, universe, initialTime);

            body.setNextLocation(updateCoordinate(body, STEPSIZE));
            body.setNextVelocity(updateVelocity(body, force, STEPSIZE));

        }
    }

    /** update coordinates of the body
     * */
    private Vector updateCoordinate(Body body, int STEPSIZE){
        return new Vector(
                (body.getLocation().x + body.getVelocity().x * STEPSIZE),
                (body.getLocation().y + body.getVelocity().y * STEPSIZE),
                (body.getLocation().z + body.getVelocity().z * STEPSIZE));
    }

    /** update velocity of the body
     * */
    private Vector updateVelocity(Body body, Vector forcesSum, int STEPSIZE){
        return new Vector(
                body.getVelocity().x + (forcesSum.x * STEPSIZE) / body.getMass(),
                body.getVelocity().y + (forcesSum.y * STEPSIZE) / body.getMass(),
                body.getVelocity().z + (forcesSum.z * STEPSIZE) / body.getMass());
    }
}
