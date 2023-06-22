package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SolarSystem;

public class Controller {

    //Gravity on Titan, maybe value is wrong
    private static Vector thrusterTarget;
    private static Vector thruster;
    private static Vector forceWind;
    private static double yCounterForce;

    public static void main() {
        forceWind = LandingForces.calculateWind(500);
        yCounterForce = forceWind.y + LandingForces.calculateGravity();
        //do methods
    }

    private static double calculateAcceleration(double force) {
        double acceleration = force / SolarSystem.landingModule.getMass();

        return acceleration;
    }

    private static double calculateVelocity() {
        return 0.0;
    }

    private static double calculateTime() {
        return 0;
    }

    private static void updateVelocity() {
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x + (thruster.x / SolarSystem.landingModule.getMass()) * PhysicsUtils.STEPSIZE, SolarSystem.landingModule.getVelocity().y + (thruster.y / SolarSystem.landingModule.getMass()) * PhysicsUtils.STEPSIZE, 0);
    }

    private void updateCoordinates() {
        SolarSystem.landingModule.setNextLocation(SolarSystem.landingModule.getLocation().x + SolarSystem.landingModule.getVelocity().x * PhysicsUtils.STEPSIZE, SolarSystem.landingModule.getLocation().y + SolarSystem.landingModule.getVelocity().y * PhysicsUtils.STEPSIZE, 0);
    }

    private static void stabilize() {
        thrusterTarget.set(forceWind.x, yCounterForce, new Vector(forceWind.x, yCounterForce, 0).getAngle(new Vector(0, 1, 0)));

        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget);
    }

    private static void changeAngle(double angleDifference) {
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

    private static void alignX(Probe probe) {

        Vector thrustToX = new Vector(thruster.x, yCounterForce, 0);

        thrusterTarget.set(thruster.x, yCounterForce, thrustToX.getAngle(new Vector(0, 1, 0)));


    }

}
