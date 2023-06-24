package com.mygdx.game.PhysicsEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.mygdx.game.Objects.LandingModule;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;

public class Controller {

    //Gravity on Titan, maybe value is wrong
    private static Vector thrusterTarget;
    private static double acceleration=0.0095;//have to decide this, it is the max acceleration we want to produce with our thrustter on 1 dimension

    private static Vector thruster;
    private static Vector forceWind;
    private static double yCounterForce;
    private static Queue<Double> rotating = new LinkedList<>();
    private static FileWriter fileWriter;
    private static BufferedWriter writer;

    public static void main() throws IOException {
        forceWind = LandingForces.calculateWind(500);
        yCounterForce = forceWind.y + LandingForces.calculateGravity();
        fileWriter = new FileWriter("core\\src\\com\\mygdx\\game\\SupportiveClasses");
        writer = new BufferedWriter(fileWriter);
        //do methods
        writer.close();
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

    private static void updateCoordinates() throws IOException {
        SolarSystem.landingModule.setNextLocation(SolarSystem.landingModule.getLocation().x + SolarSystem.landingModule.getNextVelocity().x * PhysicsUtils.STEPSIZE, SolarSystem.landingModule.getLocation().y + SolarSystem.landingModule.getNextVelocity().y * PhysicsUtils.STEPSIZE, 0);
        SolarSystem.landingModule.update();
        double rotation = rotating.remove();
        SolarSystem.landingModule.setRotation(SolarSystem.landingModule.getRotation()+rotation);
        writer.write(String.valueOf(SolarSystem.landingModule.getLocation().x) + "" + String.valueOf(SolarSystem.landingModule.getLocation().y) + "" + String.valueOf(SolarSystem.landingModule.getLocation().z) + "" + String.valueOf(SolarSystem.landingModule.getRotation()));
        writer.newLine();
    }

    private static void stabilize() throws IOException {
        double acceleration=0.0095;//have to decide this, it is the max acceleration we want to produce with our thrustter on 1 dimension
        thrusterTarget.set(forceWind.x,yCounterForce,new Vector(forceWind.x,yCounterForce,0).getAngle(new Vector(0,1,0)));
        thruster.set(thrusterTarget);
        updateVelocity();
        updateCoordinates();
       //if f=ma thing is correct 2 lines lower combine  change angle lines.

        if(SolarSystem.landingModule.getVelocity().x !=0 || SolarSystem.landingModule.getVelocity().y !=0){
        thrusterTarget.set(thrusterTarget.add(new Vector(acceleration*SolarSystem.landingModule.getMass(),acceleration*SolarSystem.landingModule.getMass(),0)));
        thrusterTarget.z= thrusterTarget.getAngle(new Vector(0,1,0));
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        double timex=SolarSystem.landingModule.getVelocity().x/acceleration;
        double timey=SolarSystem.landingModule.getVelocity().y/acceleration;
        while(timex!=0 && timey!=0){
        timex=SolarSystem.landingModule.getVelocity().x/acceleration;
        timey=SolarSystem.landingModule.getVelocity().y/acceleration;
            if (timex>1){
                SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x -acceleration,SolarSystem.landingModule.getVelocity().y,0);
            }else{
                SolarSystem.landingModule.setNextVelocity(0,SolarSystem.landingModule.getVelocity().y,0);
                thrusterTarget.set(forceWind.x,thrusterTarget.y,thrusterTarget.z);
                thrusterTarget.z= thrusterTarget.getAngle(new Vector(0,1,0));
                changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

            }
            if (timey>1){
              SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x ,SolarSystem.landingModule.getVelocity().y-acceleration,0);

            }else{
              SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x ,0,0);
              thrusterTarget.set(thrusterTarget.x,yCounterForce,thrusterTarget.z);
              thrusterTarget.z= thrusterTarget.getAngle(new Vector(0,1,0));
              changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
            }
            updateCoordinates();
        }
        }else{changeAngle(thrusterTarget.z -SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget);}
     

    }

    private static void changeAngle(double angleDifference) {
        double time = equationsOfMotion(Math.abs(angleDifference));
        //Only half of angle is calculated
        time = time*2.0;
        int stepsNeeded = (int) Math.floor(time);
        time = angleDifference/stepsNeeded;

        for(int i = 0; i < stepsNeeded; i++){
            rotating.add(time);
        }
    }

    private static double equationsOfMotion(double displacement){
        return Math.sqrt(displacement);
    }

    private static void alignX() throws IOException{
        Controller.stabilize(); 
        double distance =SolarSystem.landingModule.getLocation().x;
        double timex=2*(Math.sqrt(2*acceleration*(distance/2))/acceleration); 
        thrusterTarget.set(thrusterTarget.x+acceleration*SolarSystem.landingModule.getMass(),thrusterTarget.y,new Vector(thrusterTarget.x+acceleration*SolarSystem.landingModule.getMass(),thrusterTarget.y,0).getAngle(new Vector(0,1,0)));
        thruster.set(thrusterTarget);
        for(int i=0; i<(timex/2)-1;i++){
        SolarSystem.landingModule.setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x+acceleration,SolarSystem.landingModule.getVelocity().y,0));
        //UPDATE
        } 
        for(int i=0; i<(timex/2)-1;i++){
        SolarSystem.landingModule.setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x-acceleration,SolarSystem.landingModule.getVelocity().y,0));4        //UPDATE
        } 
        if((2*(Math.sqrt(2*acceleration*(distance/2))/acceleration))<1){
            SolarSystem.landingModule.setLocation(0,SolarSystem.landingModule.getLocation().y,SolarSystem.landingModule.getLocation().z);
        }else{Controller.alignX();}

    }

        


    }

