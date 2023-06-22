package com.mygdx.game.PhysicsEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;

public class Controller {

    //Gravity on Titan, maybe value is wrong
    public final static double titanGravity = 1.352 * Math.pow(10, -3);
    public final double gravity = titanGravity/mass;
    //Mass of landing module
    public final static double mass = 5000;
    public static Vector thrusterTarget;
    public static Vector thruster;
    public static Vector velocity;
    public static Wind wind= Wind.getwind();
    public static double yCounterForce =wind.get().y+gravity;
    public static double xCounterForce= wind.get().x;
    public static Vector coordinates;
    private final static FileWriter fileWriter;
    private static BufferedWriter writer;

    public static void main() {
        fileWriter = new FileWriter("core\\src\\com\\mygdx\\game\\SupportiveClasses");
        writer = new BufferedWriter(fileWriter);
        //do methods
        writer.close();
    }

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
    public static void updateVelocity(){
        velocity.set(velocity.x + (thruster.x/mass),velocity.y+(thruster.y/mass),0);
    }
    private void updateCoordinates(){
        coordinates.set(coordinates.x + velocity.x,coordinates.y+velocity.y,0);
        writer.write(String.valueOf(coordinates));
        writer.newLine();
    }
    public static void stabalize(){
        thrusterTarget.set(xCounterForce,yCounterForce,new Vector(xCounterForce,yCounterForce,0).getAngle(new Vector(0,1,0)));
    
        changeAngle(thrusterTarget.z - coordinates.z);
        thruster.set(thrusterTarget);
    
    }
    public static void changeAngle(double angleDifference){
        angleDifference = Math.abs(angleDifference);
        if(angleDifference<=1) {
           coordinates.z =thrusterTarget.z;
        }
        if(angleDifference<=2){
            //           coordinates.z =thrusterTarget.z 

        }
        if(angleDifference<=3){
            // coordinates.z =thrusterTarget.z 

        }
                 //  coordinates.z =thrusterTarget.z 

        
    }

    public static void alignX(Probe probe){
        double yCounterForce = wind.get().y+gravity
        double xCounterForce=  wind.get().x;

        Vector thrustToX =new Vector(thruster.x,yCounterForce,0)

        thrusterTarget.set(thruster.x,yCounterForce.y,thrustToX.getAngle(new Vector(0,1,0)))



    }
    
}
