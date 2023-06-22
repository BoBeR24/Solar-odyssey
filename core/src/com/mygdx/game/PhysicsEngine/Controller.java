package com.mygdx.game.PhysicsEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.LandingModule;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SolarSystem;

public class Controller {

    //Gravity on Titan, maybe value is wrong
    private final static double titanGravity = 1.352 * Math.pow(10, -3);
    private final static double gravity = titanGravity / SolarSystem.landingModule.getMass();
    private static Vector thrusterTarget;
    private static Vector thruster;
    private static Wind wind;
    private static Vector forceWind;
    private static double yCounterForce;

    public static void main() throws IOException {
        wind = Wind.getWind();
        //do methods
    }

    public static void setWind(double distance){
        forceWind = wind.getForceBasedOnDistance(distance);
        yCounterForce = forceWind.y + gravity;
    }

    public static double calculateAcceleration(double force) {
        double acceleration = force / SolarSystem.landingModule.getMass();

        return acceleration;
    }

    public static double calculateVelocity() {
        return 0.0;
    }

    public static double calculateTime() {
        return 0;
    }

    public static void updateVelocity() {
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x + (thruster.x / SolarSystem.landingModule.getMass()) * PhysicsUtils.STEPSIZE, SolarSystem.landingModule.getVelocity().y + (thruster.y / SolarSystem.landingModule.getMass()) * PhysicsUtils.STEPSIZE, 0);
    }

    private void updateCoordinates() {
        SolarSystem.landingModule.setNextLocation(SolarSystem.landingModule.getLocation().x + SolarSystem.landingModule.getVelocity().x * PhysicsUtils.STEPSIZE, SolarSystem.landingModule.getLocation().y + SolarSystem.landingModule.getVelocity().y * PhysicsUtils.STEPSIZE, 0);
    }

    public static void stabilize() {
        thrusterTarget.set(forceWind.x, yCounterForce, new Vector(forceWind.x, yCounterForce, 0).getAngle(new Vector(0, 1, 0)));

        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget);
    }

    public static void changeAngle(double angleDifference) {
        angleDifference = Math.abs(angleDifference);
        if (angleDifference <= 1) {
            SolarSystem.landingModule.setRotation(thrusterTarget.z);
        }
        if (angleDifference <= 2) {
            //           coordinates.z =thrusterTarget.z 

        }
        if (angleDifference <= 3) {
            // coordinates.z =thrusterTarget.z 

        }

        //  coordinates.z =thrusterTarget.z

    }

    public static void alignX(Probe probe) {

        Vector thrustToX = new Vector(thruster.x, yCounterForce, 0)

        thrusterTarget.set(thruster.x, yCounterForce, thrustToX.getAngle(new Vector(0, 1, 0)));


    }

}
