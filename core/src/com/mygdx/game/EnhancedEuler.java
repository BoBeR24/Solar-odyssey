package com.mygdx.game;

public class EnhancedEuler {

static int increment = 0;
    public static Vector[] solve(Body body, int STEPSIZE){
    
        Body body_clone = body.clone();

        Vector[] data = EulerSolver.solve(body_clone, STEPSIZE);
        body_clone.setLocation(data[0].x, data[0].y, data[0].z);
        body_clone.setVelocity(data[1].x, data[1].y, data[1].z);


        Vector forceClone = PhysicsUtils.allForce(body_clone);
        Vector forceOriginal = PhysicsUtils.allForce(body);

        // Vector forceSum = (force2.add(force1))multiply(0.5);

        Vector forceSum = (forceClone.add(forceOriginal)).multiply(0.5);
 
        Vector[] finalValues = {updateCoordinate(body_clone, STEPSIZE), updateVelocity(body_clone, forceSum, STEPSIZE)}; 

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
