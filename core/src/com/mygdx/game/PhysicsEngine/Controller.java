package com.mygdx.game.PhysicsEngine;

public class Controller {

    //Gravity on Titan, maybe value is wrong
    public final static double titanGravity = 1.352 * Math.pow(10, -3);

    //Mass of landing module
    public final static double mass = 5000;

    public static double calculateAcceleration(double force){
        double acceleration = force/mass;

        return acceleration;
    }

    public static double calculateVelocity(){
        return 0.0;
    }

    public static double calculateTime(){
        return 0;
    }
    
}
