package com.mygdx.game.PhysicsEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.mygdx.game.GameLogic.ProbeLauncher;
import com.mygdx.game.Objects.LandingModule;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;

public class Controller {
    private static double intialheight;
    private static Wind wind = Wind.getWind();
    // Gravity on Titan, maybe value is wrong
    private static Vector thrusterTarget;
    private static double acceleration = 0.0095;
    // Rotation-Stepsize
    private static Vector thruster;
    private final static double gravity = 1.352 * Math.pow(10, -3);
    private static FileWriter fileWriter;
    private static BufferedWriter writer;

    public static void main(String[] args) throws IOException {
        // Example

        ProbeLauncher.launchLandingModule(new Vector(-700, 600, 0), new Vector(0, 0, 0), 0);
        intialheight = SolarSystem.landingModule.getLocation().y;
        thrusterTarget = new Vector(0, 0, 0);
        thruster = new Vector(0, 0, 0);

        fileWriter = new FileWriter("core\\src\\com\\mygdx\\game\\SupportiveClasses\\coordinates.txt");
        writer = new BufferedWriter(fileWriter);
       //narrative / stages of landing
        noseDive();
        stabilize();
        alignX(1);
        stabilize();
        descend();
        writer.close();
    }

    public static void main() throws IOException {

    }
    //updates the velocity based on forces acting on body, is always followed by update coordinates
    private static void updateVelocity() {
        // checkThrust();
        SolarSystem.landingModule.setNextVelocity(
                SolarSystem.landingModule.getVelocity().x
                        + ((thruster.x / SolarSystem.landingModule.getMass())
                                + (wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x)
                                        / SolarSystem.landingModule.getMass())
                                * PhysicsUtils.STEPSIZE,
                SolarSystem.landingModule.getVelocity().y
                        + ((thruster.y / SolarSystem.landingModule.getMass()) - gravity
                                + (wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y)
                                        / SolarSystem.landingModule.getMass())
                                * PhysicsUtils.STEPSIZE,
                0);
    }
    //updates the coordinates based on veloctiy.

    private static void updateCoordinates() throws IOException {
        SolarSystem.landingModule.setNextLocation(
                SolarSystem.landingModule.getLocation().x
                        + SolarSystem.landingModule.getNextVelocity().x * PhysicsUtils.STEPSIZE,
                SolarSystem.landingModule.getLocation().y
                        + SolarSystem.landingModule.getNextVelocity().y * PhysicsUtils.STEPSIZE,
                0);
        SolarSystem.landingModule.update();

        SolarSystem.landingModule.setRotation(thruster.z);
        writer.write(String.valueOf(SolarSystem.landingModule.getLocation().x) + " "
                + String.valueOf(SolarSystem.landingModule.getLocation().y) + " "
                + String.valueOf(SolarSystem.landingModule.getRotation()));
        writer.newLine();
    }
//stabilised the landing module to reaach a stable position.
    private static void stabilize() throws IOException {

        thrusterTarget
                .set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                        +gravity * SolarSystem.landingModule.getMass(),
                        new Vector(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                                -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                                        + gravity * SolarSystem.landingModule.getMass(),
                                0).getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        updateVelocity();
        updateCoordinates();
        if (SolarSystem.landingModule.getVelocity().x != 0 || SolarSystem.landingModule.getVelocity().y != 0) {
            thrusterTarget.set(thrusterTarget.add(new Vector(acceleration * SolarSystem.landingModule.getMass(),
                    acceleration * SolarSystem.landingModule.getMass(), 0)));
            thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
            changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
            thruster.set(thrusterTarget);
            double timex = SolarSystem.landingModule.getVelocity().x / acceleration;
            double timey = SolarSystem.landingModule.getVelocity().y / acceleration;
            while (timex != 0 && timey != 0) {
                timex = SolarSystem.landingModule.getVelocity().x / acceleration;
                timey = SolarSystem.landingModule.getVelocity().y / acceleration;
                if (timex > 1) {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x - acceleration,
                            SolarSystem.landingModule.getVelocity().y, 0);
                } else {
                    SolarSystem.landingModule.setNextVelocity(0, SolarSystem.landingModule.getVelocity().y, 0);
                    thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                            thrusterTarget.y, thrusterTarget.z);
                    thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
                    changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

                }
                if (timey > 1) {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x,
                            SolarSystem.landingModule.getVelocity().y - acceleration, 0);

                } else {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x, 0, 0);
                    thrusterTarget.set(thrusterTarget.x,
                            wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                                    + gravity * SolarSystem.landingModule.getMass(),
                            thrusterTarget.z);
                    thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
                    changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
                    thruster.set(thrusterTarget);
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x, 0, 0);

                }
                updateCoordinates();
                timex = SolarSystem.landingModule.getVelocity().x / acceleration;
                timey = SolarSystem.landingModule.getVelocity().y / acceleration;
            }
        } else {
            changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
            thruster.set(thrusterTarget);
        }
    }

    private static void changeAngle(double angleDifference) throws IOException {

        double time = equationsOfMotion(Math.abs(angleDifference));
        time = time * 2.0;
        int stepsNeeded = (int) Math.ceil(time);
        time = angleDifference / stepsNeeded;
        if (stepsNeeded < 1) {
            thruster.set(thruster.x, thruster.y, thruster.z + angleDifference);

        } else {
            for (int i = 1; i < stepsNeeded + 1; i++) {
                thruster = thruster.add(new Vector(0, 0, time));
            }
            thruster.set(thruster.x, thruster.y, thruster.z % (2 * Math.PI));
            updateCoordinates();
        }
    }

    private static double equationsOfMotion(double displacement) {
        return Math.sqrt(displacement);
    }
// we are able to align our  module to be right above the landing pad,whilst countering wind and gravity forces.
    private static void alignX(double modifier) throws IOException {
      
        double distance = SolarSystem.landingModule.getLocation().x;
        int sign;
        if (distance > 0) {
            sign = -1;
        } else {
            sign = 1;
        }
        distance = Math.abs(SolarSystem.landingModule.getLocation().x);
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x,
                SolarSystem.landingModule.getNextVelocity().y - SolarSystem.landingModule.getNextVelocity().y,
                SolarSystem.landingModule.getNextVelocity().z);
        thrusterTarget.set(thrusterTarget.x + sign * acceleration * modifier * SolarSystem.landingModule.getMass(),
                SolarSystem.landingModule.getMass() * gravity
                        + -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y,
                new Vector(thrusterTarget.x + sign * acceleration * modifier * SolarSystem.landingModule.getMass(),
                        SolarSystem.landingModule.getMass() * gravity
                                + -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y,
                        0)
                        .getAngle(new Vector(0, 1, 0)));

        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget);
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x,
                SolarSystem.landingModule.getNextVelocity().y - SolarSystem.landingModule.getNextVelocity().y,
                SolarSystem.landingModule.getNextVelocity().z);
        int tracker = 0;
        while (Math.abs(SolarSystem.landingModule.getLocation().x) > (distance / 2)) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(
                            SolarSystem.landingModule.getVelocity().x + sign * acceleration * modifier,
                            SolarSystem.landingModule.getVelocity().y
                                    + ((thruster.y / SolarSystem.landingModule.getMass()) - gravity
                                            + wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y)
                                            * PhysicsUtils.STEPSIZE,
                            0));
            updateCoordinates();
            tracker++;
        }

        thrusterTarget.set(-thrusterTarget.x, thrusterTarget.y,
                new Vector(-thrusterTarget.x, thrusterTarget.y, 0)
                        .getAngle(new Vector(0, 1, 0)));

        thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x,
                SolarSystem.landingModule.getNextVelocity().y - SolarSystem.landingModule.getNextVelocity().y,
                SolarSystem.landingModule.getNextVelocity().z);
        thruster.set(thrusterTarget);
        while (tracker > 0) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(
                            SolarSystem.landingModule.getVelocity().x - sign * acceleration * modifier,
                            SolarSystem.landingModule.getVelocity().y
                                    + (thruster.y / SolarSystem.landingModule.getMass()) - gravity
                                    + (wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y)
                                            * PhysicsUtils.STEPSIZE,
                            0));
            updateCoordinates();// A check if update coordinates is actually all we need
            tracker--;

        }

        if ((2 * (Math.sqrt(2 * acceleration * modifier * (distance / 2)) / acceleration * modifier)) < 0.00001) {
            SolarSystem.landingModule.setNextLocation(0, SolarSystem.landingModule.getLocation().y,
                    SolarSystem.landingModule.getLocation().z);
            // SolarSystem.landingModule.setNextVelocity(0,SolarSystem.landingModule.getVelocity().y,0);
            thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                    -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                            + gravity * SolarSystem.landingModule.getMass(),
                    new Vector(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                            -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                                    + gravity * SolarSystem.landingModule.getMass(),
                            0.0)
                            .getAngle(new Vector(0, 1, 0)));
            changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
            thruster.set(thrusterTarget);
            updateVelocity();
            updateCoordinates();

        } else {

            Controller.alignX(modifier / 4);
        }
    }
//function to find the target force we must emit to obtain certain speed in certain time. from suvat equations from motion.
    public static double findTargetForce(double speed, double time) {
        return ((speed - SolarSystem.landingModule.getVelocity().y) / time) * SolarSystem.landingModule.getMass();
    }
//function to find the target distance based on accelerrtion and velocity. from suvat equations from motion.

    private static double findTargetDistance(double acceleration) {
        return Math.pow(SolarSystem.landingModule.getVelocity().y, 2) / (2 * acceleration);
    }
//descend straight downwards, countering wind and gravity appropriately.
    private static void descend() throws IOException {

        double time = 20;
        double targetForce = findTargetForce(1, time);

        double scalar = acceleration * (1 / intialheight);// have to find value such that the thruster doesnt emit more
                                                          // than it is allowed
        double distance = SolarSystem.landingModule.getLocation().y;
        // double originaly= thrusterTarget.y;
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                        + gravity * SolarSystem.landingModule.getMass() - targetForce,
                0.0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        for (int i = 0; i < time; i++) {
            updateVelocity();
            updateCoordinates();
        }

        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                        + gravity * SolarSystem.landingModule.getMass(),
                0.0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        double limit = findTargetDistance(targetForce / SolarSystem.landingModule.getMass());
        while (SolarSystem.landingModule.getLocation().y > limit + 5) {
            updateVelocity();
            updateCoordinates();
            if ((-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x != thruster.x)
                    || (-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y != thruster.y
                            - gravity * SolarSystem.landingModule.getMass())) {
                thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                        -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                                + gravity * SolarSystem.landingModule.getMass(),
                        0.0);
                thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
                thruster.set(thrusterTarget);
                changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            }
        }
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                        + gravity * SolarSystem.landingModule.getMass() + targetForce,
                0.0);
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y,
                thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        for (int i = 0; i < time; i++) {
            updateVelocity();
            updateCoordinates();
            if ((-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x != thruster.x)) {
                thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                        -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                                + gravity * SolarSystem.landingModule.getMass() + targetForce,
                        0.0);
                thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                        thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
                thruster.set(thrusterTarget);
                changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            }

        }
        time = 3;
        targetForce = findTargetForce(-0.0001, time);
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                        + gravity * SolarSystem.landingModule.getMass() + targetForce,
                0.0);
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y,
                thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());

        for (int i = 0; i < time; i++) {
            updateVelocity();
            updateCoordinates();

        }

        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,
                -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y
                        + gravity * SolarSystem.landingModule.getMass(),
                0.0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        while (SolarSystem.landingModule.getLocation().y > 0.0001) {
            updateVelocity();
            updateCoordinates();
            if ((wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x == 0)) {
                thrusterTarget.set(0, 0 + gravity * SolarSystem.landingModule.getMass(), 0.0);
                thrusterTarget.set(0, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
                thruster.set(thrusterTarget);
                changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            }
        }

        if (confirmland()) {
            System.out.println("Succesfully landed, with all parameters met!");
        }
    }
        //returns true if paraemeters are met, meaning if we have landed.
    private static boolean confirmland() throws IOException {
        if (Math.abs(SolarSystem.landingModule.getLocation().x) > 0.0001) {
            double sign;
            double maxsingledistance = (0.5 * (acceleration) * Math.sqrt(0.5)) * 2;
            if (SolarSystem.landingModule.getLocation().x < 0) {
                sign = 1;
            } else {
                sign = -1;
            }

            if ((2 * ((Math.sqrt(Math.abs(SolarSystem.landingModule.getLocation().x))) * acceleration)
                    / acceleration) > 1) {
                while ((2 * ((Math.sqrt(Math.abs(SolarSystem.landingModule.getLocation().x))) * acceleration)
                        / acceleration) > 1) {
                    SolarSystem.landingModule.setLocation(
                            SolarSystem.landingModule.getLocation().x + (sign * maxsingledistance),
                            SolarSystem.landingModule.getLocation().y, SolarSystem.landingModule.getLocation().y);
                    SolarSystem.landingModule.setNextVelocity(0, 0, SolarSystem.landingModule.getNextVelocity().z);
                    // include thruster for gui purposes
                    updateCoordinates();
                }
                if (2 * (((Math.sqrt(Math.abs(SolarSystem.landingModule.getLocation().x))) * acceleration)
                        / acceleration) < 1
                        && 2 * (((Math.sqrt(Math.abs(SolarSystem.landingModule.getLocation().x))) * acceleration)
                                / acceleration) > 0) {
                    SolarSystem.landingModule.setLocation(0, 0, 0);
                } else {
                    System.out.println("failed at reaching appropriate horizontal coordinate interval");
                    return false;
                }
            }
        }
        if (Math.abs(SolarSystem.landingModule.getRotation()) > 20000) {// check angle bound
            System.out.println("failed at reaching appropriate angle interval");
            return false;
            // probably have to add correcter but theres a chane it will always be straight
            // up based on the descend.
        }
        if (Math.abs(SolarSystem.landingModule.getVelocity().x) > 0.0001) {
            System.out.println("failed at reaching minimal horizontal speed");
            return false;// exact same reason as above we should naturally attain this through our
                         // descend and align x methods
        }
        if (Math.abs(SolarSystem.landingModule.getVelocity().y) > 0.0001) {
            System.out.println("failed at reaching minimal vertical speed");
            return false;// again, same reason, descend and landing correcter are responsible for this
        }
        return true;

    }
// forces the landing module to nosedive downwards, allowing us to lower altitude significantly at a fast rate.
    private static void noseDive() throws IOException {
        double targetHeight = SolarSystem.landingModule.getLocation().y;
        while (SolarSystem.landingModule.getLocation().y > targetHeight * (1.0 / 3.0)) {
            double xComponenet = -1 * SolarSystem.landingModule.getLocation().x;
            double yComponent = -1 * SolarSystem.landingModule.getLocation().y;

            if (Math.abs(xComponenet) > Math.abs(yComponent)) {
                yComponent = (yComponent / xComponenet) * acceleration;
                xComponenet = acceleration;
            } else {
                xComponenet = (-xComponenet / yComponent) * acceleration;
                yComponent = -acceleration;
            }
            thrusterTarget.set(xComponenet, yComponent, 0);
            thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
            changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
            thruster.set(thrusterTarget);
            updateVelocity();
            updateCoordinates();
        }

    }
}
