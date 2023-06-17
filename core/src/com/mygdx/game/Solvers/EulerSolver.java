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
    private float STEPSIZE;

    /** method to initiate solving process.
     * @param universe states for which set of body we want approximate next state
     * */
    @Override
    public void calculateNextState(ArrayList<Body> universe, Function function, float initialTime, float stepSize){
        this.STEPSIZE = stepSize;
        for (Body body : universe) {
//            Vector force = function.evaluate(body, universe, initialTime);

            Vector coordinateChange = function.calculateCoordinateChange(body, universe, initialTime);
            Vector velocityChange = function.calculateVelocityChange(body, universe, initialTime);

            body.setNextLocation(nextCoordinate(body, coordinateChange));
            body.setNextVelocity(nextVelocity(body, velocityChange));

        }
    }

    /** update coordinates of the body
     * */
    private Vector nextCoordinate(Body body, Vector coordinateChange){
        return new Vector(
                (body.getLocation().x + coordinateChange.x * STEPSIZE),
                (body.getLocation().y + coordinateChange.y * STEPSIZE),
                (body.getLocation().z + coordinateChange.z * STEPSIZE)
        );
    }

    /** update velocity of the body
     * */
    private Vector nextVelocity(Body body, Vector velocityChange){
        return new Vector(
                body.getVelocity().x + velocityChange.x * STEPSIZE,
                body.getVelocity().y + velocityChange.y * STEPSIZE,
                body.getVelocity().z + velocityChange.z * STEPSIZE
        );
    }
}
