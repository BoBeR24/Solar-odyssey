package com.mygdx.game;

public class EulerSolver {
    public static Vector[] solve(Body body, int STEPSIZE){
        Vector force = PhysicsUtils.allForce(body);

        Vector[] newValues = new Vector[2];

        newValues[0] = updateCoordinate(body, STEPSIZE);
        newValues[1] = updateVelocity(body, force, STEPSIZE);

        return newValues;
    }

    private static Vector updateCoordinate(Body body, int STEPSIZE){
        return new Vector(
                (body.getLocation().x + body.getVelocity().x * STEPSIZE),
                (body.getLocation().y + body.getVelocity().y * STEPSIZE),
                (body.getLocation().z + body.getVelocity().z * STEPSIZE));
    }

    private static Vector updateVelocity(Body body, Vector forcesSum, int STEPSIZE){
        return new Vector(
                body.getVelocity().x + (forcesSum.x * STEPSIZE) / body.getMass(),
                body.getVelocity().y + (forcesSum.y * STEPSIZE) / body.getMass(),
                body.getVelocity().z + (forcesSum.z * STEPSIZE) / body.getMass());
    }
}
