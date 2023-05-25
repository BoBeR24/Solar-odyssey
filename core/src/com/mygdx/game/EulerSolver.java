package com.mygdx.game;

/** One of several different ODE solver methods based on classic 1st order Euler's method.
 *
 * */
public class EulerSolver {
    /** method to initiate solving process.
     * @param body body to which we calculate next state
     * @param STEPSIZE step size for the solver
     * */
    public static Vector[] solve(Body body, int STEPSIZE){
        Vector force = PhysicsUtils.allForce(body);

        Vector[] newValues = new Vector[2];

        newValues[0] = updateCoordinate(body, STEPSIZE);
        newValues[1] = updateVelocity(body, force, STEPSIZE);

        return newValues;
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
}
