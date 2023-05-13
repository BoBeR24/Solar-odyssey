package com.mygdx.game;

public class Rk4 {
 
    public static void kCalculator(Body body, Vector forceSum, int STEPSIZE) {
        Vector k1Pos, k2Pos, k3Pos, k4Pos;
        Vector k1Vel, k2Vel, k3Vel, k4Vel;

        Vector initialPos;
        Vector initialVel;

        //Calculated with current time
        k1Vel = updateVelocity(body, forceSum, initialVel);
        k1Pos = updateCoordinate(body, initialVel, initialPos);

        //Change time = time + stepsize/2, calculate with k1 values
        k2Vel = updateVelocity(body, forceSum, initialVel.add(k1Vel.multiply(0.5)));
        k2Pos = updateCoordinate(body, initialVel.add(k1Vel.multiply(0.5)), initialPos.add(k1Pos.multiply(0.5)));

        //Keep same time as k2, but calculate with k2 values
        k3Vel = updateVelocity(body, forceSum, initialVel.add(k2Vel.multiply(0.5)));
        k3Pos = updateCoordinate(body, initialVel.add(k2Vel.multiply(0.5)), initialPos.add(k2Pos.multiply(0.5)));

        //Change time = time + stepsize
        k4Vel = updateVelocity(body, forceSum, initialVel.add(k3Vel));
        k4Pos = updateCoordinate(body, initialVel.add(k3Vel), initialPos.add(k3Pos));

        Vector finalVelocity = initialVel.add(k1Vel.add(k2Vel.multiply(2).add(k3Vel.multiply(2).add(k4Vel))).multiply(STEPSIZE/6));
        Vector finalPosition = initialPos.add(k1Pos.add(k2Pos.multiply(2).add(k3Pos.multiply(2).add(k4Pos))).multiply(STEPSIZE/6));

    }

    private static Vector updateVelocity(Body body, Vector forcesSum, Vector velocity){
        //Same as physicsUTIL but dont change state yet
    }
    private static Vector updateCoordinate(Body body, Vector velocity, Vector position){
        //Same as physicsUTIL but dont change state yet
    }

}
