package com.mygdx.game;

public class EnhancedEuler {

    public static Vector[] solve(Body body, int STEPSIZE){
    
        Body body_clone = body.clone();

        //Calculate next set of values with normal euler solver
        Vector[] data = EulerSolver.solve(body_clone, STEPSIZE);
        //Update state of cloned body with new values
        body_clone.setLocation(data[0].x, data[0].y, data[0].z);
        body_clone.setVelocity(data[1].x, data[1].y, data[1].z);

        //Calculate forces with old and new body
        Vector forceClone = PhysicsUtils.allForce(body_clone);
        Vector forceOriginal = PhysicsUtils.allForce(body);

        //Average the force vector previously calculated
        Vector forceSum = (forceClone.add(forceOriginal)).multiply(0.5);
 
        //Calculate velocity and position with old body and new average force vector
        Vector[] finalValues = {updateCoordinate(body, STEPSIZE), updateVelocity(body, forceSum, STEPSIZE)}; 

        return finalValues;
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
