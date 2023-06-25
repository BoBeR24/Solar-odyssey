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
     private static   double intialheight;
    private static Wind wind =  Wind.getWind();
    // Gravity on Titan, maybe value is wrong
    private static Vector thrusterTarget;
    private static double acceleration = 0.0095;// have to decide this, it is the max acceleration we want to produce
                                                // with our thrustter on 1 dimension

    private static Vector thruster;
    //private static Vector forceWind;
    //private static double yCounterForce;
    private final static double gravity = 1.352 * Math.pow(10, -3);
    private static Queue<Double> rotating = new LinkedList<>();
    private static FileWriter fileWriter;
    private static BufferedWriter writer;
    public static void main(String[] args) throws IOException {
          // Example
         
        
        ProbeLauncher.launchLandingModule(new Vector(-400, 400, 0), new Vector(0, 0, 0), 0);
         intialheight=SolarSystem.landingModule.getLocation().y;
         thrusterTarget = new Vector(0,0,0);
         thruster = new Vector(0,0,0);

        fileWriter = new FileWriter("D:\\project 1-2\\Solar-odyssey\\core\\src\\com\\mygdx\\game\\SupportiveClasses\\coordinates.txt");
        writer = new BufferedWriter(fileWriter);
        // do methods
       
        
        noseDive();
        stabilize();
        alignX();
        stabilize();
        descend();
        landCorrrecter();
        

        writer.close();
    }
    public static void main() throws IOException {
       
      /*  // Example
        ProbeLauncher.launchLandingModule(new Vector(500, 500, 0), new Vector(500, 500, 0), 1.0);
        double a= SolarSystem.landingModule.getVelocity().x;
        a=a+9999999;
        fileWriter = new FileWriter("core\\src\\com\\mygdx\\game\\SupportiveClasses\\coordinates.txt");
        writer = new BufferedWriter(fileWriter);
        // do methods
        updateCoordinates();
        writer.close();*/
    }

    private static double calculateAcceleration(double force) {
        double acceleration = force / SolarSystem.landingModule.getMass();

        return acceleration;
    }
    

    private static void updateVelocity() {
        SolarSystem.landingModule.setNextVelocity(
                SolarSystem.landingModule.getVelocity().x
                        + (thruster.x / SolarSystem.landingModule.getMass()) * PhysicsUtils.STEPSIZE,
                SolarSystem.landingModule.getVelocity().y
                        + (thruster.y / SolarSystem.landingModule.getMass()) * PhysicsUtils.STEPSIZE,
                0);
    }

    private static void updateCoordinates() throws IOException {
        SolarSystem.landingModule.setNextLocation(
                SolarSystem.landingModule.getLocation().x
                        + SolarSystem.landingModule.getNextVelocity().x * PhysicsUtils.STEPSIZE,
                SolarSystem.landingModule.getLocation().y
                        + SolarSystem.landingModule.getNextVelocity().y * PhysicsUtils.STEPSIZE,
                0);
        SolarSystem.landingModule.update();
        if (!rotating.isEmpty()) {
            double rotation = rotating.remove();
            SolarSystem.landingModule.setRotation(SolarSystem.landingModule.getRotation() + rotation);
        }
        writer.write(String.valueOf(SolarSystem.landingModule.getLocation().x) + " "
                + String.valueOf(SolarSystem.landingModule.getLocation().y) + " "
                + String.valueOf(SolarSystem.landingModule.getRotation()));
        writer.newLine();
    }

    private static void stabilize() throws IOException {
   
        thrusterTarget.set(wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity,
                new Vector(wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity, 0).getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        updateVelocity();
        updateCoordinates();
        // if f=ma thing is correct 2 lines lower combine change angle lines.

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
                    thrusterTarget.set(wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y, thrusterTarget.z);
                    thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
                    changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

                }
                if (timey > 1) {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x,
                            SolarSystem.landingModule.getVelocity().y - acceleration, 0);

                } else {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x, 0, 0);
                    thrusterTarget.set(thrusterTarget.x,wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity, thrusterTarget.z);
                    thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
                    changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
                }
                updateCoordinates();
            }
        } else {
            changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
            thruster.set(thrusterTarget);
        }

    }

    private static void changeAngle(double angleDifference) {
        double time = equationsOfMotion(Math.abs(angleDifference));
        // Only half of angle is calculated
        time = time * 2.0;
        int stepsNeeded = (int) Math.floor(time);
        time = angleDifference / stepsNeeded;

        for (int i = 0; i < stepsNeeded; i++) {
            rotating.add(time);
        }
    }

    private static double equationsOfMotion(double displacement) {
        return Math.sqrt(displacement);
    }

    private static void alignX() throws IOException {
        Controller.stabilize();
        double distance = SolarSystem.landingModule.getLocation().x;
        double timex = 2 * (Math.sqrt(2 * acceleration * (distance / 2)) / acceleration);
        thrusterTarget.set(thrusterTarget.x + acceleration * SolarSystem.landingModule.getMass(), thrusterTarget.y,
                new Vector(thrusterTarget.x + acceleration * SolarSystem.landingModule.getMass(), thrusterTarget.y, 0)
                        .getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

        for (int i = 0; i < (timex / 2) - 1; i++) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x + acceleration,
                            SolarSystem.landingModule.getVelocity().y, 0));
         updateCoordinates();//A
        }
        thrusterTarget.set(thrusterTarget.x - acceleration * SolarSystem.landingModule.getMass(), thrusterTarget.y,
                new Vector(thrusterTarget.x - acceleration * SolarSystem.landingModule.getMass(), thrusterTarget.y, 0)
                        .getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

        for (int i = 0; i < (timex / 2) - 1; i++) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x - acceleration,
                            SolarSystem.landingModule.getVelocity().y, 0));
           updateCoordinates();//A check if update coordinates is actually all we need 
        }
        if ((2 * (Math.sqrt(2 * acceleration * (distance / 2)) / acceleration)) < 1) {
            SolarSystem.landingModule.setLocation(0, SolarSystem.landingModule.getLocation().y,
                    SolarSystem.landingModule.getLocation().z);
        } else {
            Controller.alignX();
        }

    }
    private static void descend() throws IOException {
        double scalar = acceleration*(1/intialheight);// have to find value such that the thruster doesnt emit more than it is allowed
        double distance = SolarSystem.landingModule.getLocation().y;
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y - scalar*(Math.pow(distance, 2)) , 0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        while (SolarSystem.landingModule.getLocation().y > 0.01) {
            distance = SolarSystem.landingModule.getLocation().y;
            thrusterTarget.set(thrusterTarget.x, thrusterTarget.y - scalar*(Math.pow(distance, 2)) , 0);
            thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
            thruster.set(thrusterTarget);
            changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            updateVelocity();
            updateCoordinates();
            if (SolarSystem.landingModule.getLocation().y + (SolarSystem.landingModule.getNextVelocity().y
                    + (thrusterTarget.y - (Math.pow(distance, 2)) * scalar)) <= 0) {
                break;
            }
        }
        // either we include landing and checks here, or we make another methodS

    }

    private static void landCorrrecter() throws IOException {
        double distance = SolarSystem.landingModule.getLocation().y;
        double timey = 2 * (Math.sqrt(distance * acceleration) / acceleration);
        if (timey > 1) {
            thrusterTarget.set(thrusterTarget.x, thrusterTarget.y - acceleration / SolarSystem.landingModule.getMass(),
                    0);
            thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
            thruster.set(thrusterTarget);
            changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            while (timey > 1) {
                updateVelocity();
                updateCoordinates();
            }
        }
        SolarSystem.landingModule.setLocation(SolarSystem.landingModule.getLocation().x, 0,
                SolarSystem.landingModule.getLocation().y);
        stabilize();
        if (confirmland()) {
            System.out.println("succesfully landed!");
        } else {
            System.out.println("landing failed");
        }
    }

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
        if(Math.abs(SolarSystem.landingModule.getRotation())>0.02){//check angle bound
            System.out.println("failed at reaching appropriate angle interval");
            return false;
            //probably have to add correcter but theres a chane it will always be straight up based on the descend. 
        }
        if(Math.abs(SolarSystem.landingModule.getVelocity().x)>0.0001){
            System.out.println("failed at reaching minimal horizontal speed");
            return false;//exact same reason as above we should naturally attain this through our descend and align x methods
        }
        if(Math.abs(SolarSystem.landingModule.getVelocity().y)>0.0001){
            System.out.println("failed at reaching minimal vertical speed");
            return false;//again, same reason, descend and landing correcter are responsible for this 
        }
        return true;
        
    }
    private static void noseDive() throws IOException{
        double targetHeight=SolarSystem.landingModule.getLocation().y;
       while(SolarSystem.landingModule.getLocation().y>targetHeight*(2/3)){
        double xComponenet = -1*SolarSystem.landingModule.getLocation().x;
       double yComponent = -1*SolarSystem.landingModule.getLocation().y;
       
       if(xComponenet>yComponent){
        yComponent= (yComponent/xComponenet)*acceleration;
        xComponenet=acceleration;
       }else{
        xComponenet=(xComponenet/yComponent)*acceleration;
        yComponent=acceleration;
       }
        thrusterTarget.set(xComponenet,yComponent,0);
       thrusterTarget.z = thrusterTarget.getAngle(new Vector(0,1,0));
       changeAngle(thrusterTarget.z-SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget); 
        updateVelocity();
        updateCoordinates();
        }

    }
}

