package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;

public class Controller {

    //Gravity on Titan, maybe value is wrong
    public final static double titanGravity = 1.352 * Math.pow(10, -3);
    public final double gravity = titanGravity/mass;
    //Mass of landing module
    public final static double mass = 5000;
    public Vector thrusterTarget;
    public Vector thruster;
    public Vector velocity;
    public Wind wind= Wind.getwind();
    public double yCounterForce =wind.get().y+gravity;
    public double xCounterForce= wind.get().x;
    public Vector coordinates;

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
    private static void updateCoordinates(){
        coordinates.set(coordinates.x + velocity.x,coordinates.y+velocity.y,0);
    }
     
    public static void stabalize(){
        double acceleration;//have to decide this, it is the max acceleration we want to produce with our thrustter on 1 dimension
        thrusterTarget.set(xCounterForce,yCounterForce,new Vector(xCounterForce,yCounterForce,0).getAngle(new Vector(0,1,0)));
        changeAngle(thrusterTarget.z - coordinates.z);
        thruster.set(thrusterTarget);
        updateVelocity();
        updateCoordinates();

        if(velocity.x !=0 || velocity.y !=0){
        thrusterTarget.set(thrusterTarget.add(new Vector(acceleration/mass,acceleration/mass,0)))
        thrusterTarget.z= thrusterTarget.getAngle(getAngle(new Vector(0,1,0)));
        changeAngle(thrusterTarget.z - coordinates.z);
        double timex=velocity.x/acceleration;
        double timey=velocity.y/acceleration
        while(timex!=0 && timey!=0){
            if (timex>1){
                velocity.x -= acceleration;
            }else{
                velocity.x=0;
            }
            if (timey>1){
                velocity.y -= acceleration;

            }else{
                velocity.y=0;
            }
            updateVelocity();
            updateCoordinates();
        }
        }

    
    }
    public static void changeAngle(double angleDifference){
        Math.abs(angleDifference)
        if(angleDifference<=1) {
           coordinates.z =thrusterTarget.z 
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
        double yCounterForce =wind.get().y+gravity
        double xCounterForce= wind.get().x;

        Vector thrustToX =new Vector(thruster.x,yCounterForce,0)

        thrusterTarget.set(thruster.x,yCounterForce.y,thrustToX.getAngle(new Vector(0,1,0)))



    }
    public static Vector 
    
}
