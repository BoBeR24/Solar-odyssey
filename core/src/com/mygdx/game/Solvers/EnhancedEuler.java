package com.mygdx.game.Solvers;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Properties.SolarSystem;

import java.util.ArrayList;

public class EnhancedEuler implements Solver{
    private float STEPSIZE;
    public void calculateNextState(ArrayList<Body> universe, Function function, float initialTime, float stepSize){
        this.STEPSIZE = stepSize;

        Solver solver = new EulerSolver();


        ArrayList<Body> cloned_universe = new ArrayList<>();

        for (Body body: universe) {
            cloned_universe.add(body.clone());
        }

        //Calculate next set of values with default euler solver approach
        solver.calculateNextState(cloned_universe, function, initialTime, stepSize);
        updateUniverse(cloned_universe);

        for (Body body : universe) {
            // calculate coordinate and velocity change for initial state(so evaluate Yn)
            Vector originalCoordinateChange = function.calculateCoordinateChange(body, universe, initialTime);
            Vector originalVelocityChange = function.calculateVelocityChange(body, universe, initialTime);

            // do the same for the cloned universe(as cloned universe is currently in next state(Yn+1))
            Vector clonedCoordinateChange = function.calculateCoordinateChange(cloned_universe.get(body.getId()),
                    cloned_universe, initialTime);
            Vector clonedVelocityChange = function.calculateVelocityChange(cloned_universe.get(body.getId()),
                    cloned_universe, initialTime);

            // now find average between next step approximation and current state
            Vector averageCoordinateChange = originalCoordinateChange.add(clonedCoordinateChange).multiply(0.5);
            Vector averageVelocityChange = originalVelocityChange.add(clonedVelocityChange).multiply(0.5);

            // use this approximation to calculate new, more accurate approximation
            body.setNextLocation(nextCoordinate(body, averageCoordinateChange));
            body.setNextVelocity(nextVelocity(body, averageVelocityChange));
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

    /** Updates whole given universe(each body from it)
     * */
    private static void updateUniverse(ArrayList<Body> universeToUpdate){
        for (Body body : universeToUpdate) {
            body.update();
        }
    }
}
