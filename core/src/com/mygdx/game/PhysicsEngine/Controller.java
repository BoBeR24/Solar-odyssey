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
//Rotation-Stepsize 
    private static Vector thruster;
    private static double modifier;
    //private static Vector forceWind;
    //private static double yCounterForce;
    private final static double gravity = 1.352 * Math.pow(10, -3);
    private static FileWriter fileWriter;
    private static BufferedWriter writer;
    public static void main(String[] args) throws IOException {
          // Example
         
        
        ProbeLauncher.launchLandingModule(new Vector(-700, 600, 0), new Vector(0, 0, 0), 0);
         intialheight=SolarSystem.landingModule.getLocation().y;
         thrusterTarget = new Vector(0,0,0);
         thruster = new Vector(0,0,0);

        fileWriter = new FileWriter("core\\src\\com\\mygdx\\game\\SupportiveClasses\\coordinates.txt");
        writer = new BufferedWriter(fileWriter);
        // do methods
       // -1*wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x;
       // -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y;

        
        noseDive();
    stabilize();
      alignX(1);
       stabilize();
       //System.out.println(SolarSystem.landingModule.getVelocity());
        descend();
       // landCorrrecter();
        

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
    
    private static void checkThrust(){
        if(thruster.x/SolarSystem.landingModule.getMass()>acceleration){
            thruster.set(acceleration*SolarSystem.landingModule.getMass(),thruster.y,thruster.z);
            System.err.println("changex");
        }
        if(thruster.y/SolarSystem.landingModule.getMass()>acceleration){
                thruster.set(thruster.x,acceleration*SolarSystem.landingModule.getMass(),thruster.z);
                System.out.println("changey");
        }
    }
    
    private static void updateVelocity() {
       // checkThrust();
        SolarSystem.landingModule.setNextVelocity(
                SolarSystem.landingModule.getVelocity().x
                        + ((thruster.x / SolarSystem.landingModule.getMass()) +(wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x)/SolarSystem.landingModule.getMass() )* PhysicsUtils.STEPSIZE,
                SolarSystem.landingModule.getVelocity().y
                        + ((thruster.y / SolarSystem.landingModule.getMass()) - gravity +(wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y)/SolarSystem.landingModule.getMass() )* PhysicsUtils.STEPSIZE,
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
        
        SolarSystem.landingModule.setRotation(thruster.z);
        writer.write(String.valueOf(SolarSystem.landingModule.getLocation().x) + " "
                + String.valueOf(SolarSystem.landingModule.getLocation().y) + " "
                + String.valueOf(SolarSystem.landingModule.getRotation()));
        writer.newLine();
    }

    private static void stabilize() throws IOException {
   
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, +gravity * SolarSystem.landingModule.getMass(),
                new Vector(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity * SolarSystem.landingModule.getMass(), 0).getAngle(new Vector(0, 1, 0)));
                thruster.set(thrusterTarget);
                //change angle
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
                    thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y, thrusterTarget.z);
                    thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
                    changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

                }
                if (timey > 1) {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x,
                            SolarSystem.landingModule.getVelocity().y - acceleration, 0);

                } else {
                    SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getVelocity().x, 0, 0);
                    thrusterTarget.set(thrusterTarget.x,wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity*SolarSystem.landingModule.getMass(), thrusterTarget.z);
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
        System.out.println("done with stabalize");
    }

    private static void changeAngle(double angleDifference) throws IOException {
     //   Vector temp = thruster;
        
      // thruster.set(0,0,thruster.z);
        double time = equationsOfMotion(Math.abs(angleDifference));
        // Only half of angle is calculated
        time = time * 2.0;
        int stepsNeeded = (int) Math.ceil(time);
        time = angleDifference / stepsNeeded;
        if(stepsNeeded<1){
            thruster.set(thruster.x,thruster.y,thruster.z+angleDifference);
            
        }else{
           for (int i = 1; i < stepsNeeded+1; i++) {
            thruster = thruster.add(new Vector(0,0,time));
            

           }
            thruster.set(thruster.x,thruster.y,thruster.z%(2*Math.PI));
         //   updateVelocity();
            updateCoordinates();
          //  thruster.set(temp);

        }
    }

    private static double equationsOfMotion(double displacement) {
        return Math.sqrt(displacement);
    }

   /*  private static void alignX() throws IOException {
       // Controller.stabilize();
       double distance= SolarSystem.landingModule.getLocation().x;
        int sign;
        if(distance>0){
             sign =-1;
        }else{
            sign=1;
        }
         distance = Math.abs(SolarSystem.landingModule.getLocation().x);
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x, SolarSystem.landingModule.getNextVelocity().y-SolarSystem.landingModule.getNextVelocity().y, SolarSystem.landingModule.getNextVelocity().z);
        double timex = 2 * (Math.sqrt( acceleration * distance  )/ acceleration);
        thrusterTarget.set(thrusterTarget.x + sign* acceleration * SolarSystem.landingModule.getMass(), SolarSystem.landingModule.getMass()*gravity,
                new Vector(thrusterTarget.x + sign*acceleration * SolarSystem.landingModule.getMass(), thrusterTarget.y, 0)
                        .getAngle(new Vector(0, 1, 0)));
        
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget);
         SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x, SolarSystem.landingModule.getNextVelocity().y-SolarSystem.landingModule.getNextVelocity().y, SolarSystem.landingModule.getNextVelocity().z);
        double test = -SolarSystem.landingModule.getLocation().x;// remove
        int testt=400;
        for (int i = 0; i < (int)(timex / 2) - 1; i++) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x + sign* acceleration,
                            SolarSystem.landingModule.getVelocity().y
                        + ((thruster.y / SolarSystem.landingModule.getMass())- gravity )* PhysicsUtils.STEPSIZE, 0));
         updateCoordinates();//A
            if(test>Math.abs(SolarSystem.landingModule.getLocation().x)){
                test=Math.abs(SolarSystem.landingModule.getLocation().x);//remove entire if
                testt =i;
            }
        }System.out.println(testt);
        thrusterTarget.set(-thrusterTarget.x , thrusterTarget.y,
                new Vector(-thrusterTarget.x , thrusterTarget.y, 0)
                        .getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());

        for (int i = 0; i < (timex / 2) - 1; i++) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x - acceleration,
                            SolarSystem.landingModule.getVelocity().y
                        + (thruster.y / SolarSystem.landingModule.getMass())- gravity * PhysicsUtils.STEPSIZE, 0));
           updateCoordinates();//A check if update coordinates is actually all we need 
        }
        if ((2 * (Math.sqrt(2 * acceleration * (distance / 2)) / acceleration)) < 1) {
            SolarSystem.landingModule.setLocation(0, SolarSystem.landingModule.getLocation().y,
                    SolarSystem.landingModule.getLocation().z);
        } else {
            Controller.alignX();
            System.out.println("L");
        }

    }*/
    private static void alignX(double modifier) throws IOException {
       // Controller.stabilize();
     // Controller.stabilize();
       double distance= SolarSystem.landingModule.getLocation().x;
        int sign;
        if(distance>0){
             sign =-1;
        }else{
            sign=1;
        }
         distance = Math.abs(SolarSystem.landingModule.getLocation().x);
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x, SolarSystem.landingModule.getNextVelocity().y-SolarSystem.landingModule.getNextVelocity().y, SolarSystem.landingModule.getNextVelocity().z);
        thrusterTarget.set(thrusterTarget.x + sign* acceleration*modifier * SolarSystem.landingModule.getMass(), SolarSystem.landingModule.getMass()*gravity + -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y,
                new Vector(thrusterTarget.x + sign*acceleration*modifier * SolarSystem.landingModule.getMass(),SolarSystem.landingModule.getMass()*gravity + -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y , 0)
                        .getAngle(new Vector(0, 1, 0)));
        
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        thruster.set(thrusterTarget);
         SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x, SolarSystem.landingModule.getNextVelocity().y-SolarSystem.landingModule.getNextVelocity().y, SolarSystem.landingModule.getNextVelocity().z);
       int tracker=0;
       while(Math.abs(SolarSystem.landingModule.getLocation().x)>(distance/2)) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x + sign* acceleration*modifier,
                            SolarSystem.landingModule.getVelocity().y
                        + ((thruster.y / SolarSystem.landingModule.getMass())- gravity +wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y )* PhysicsUtils.STEPSIZE, 0));
         updateCoordinates();
        tracker++;
        }
        
    
        thrusterTarget.set(-thrusterTarget.x , thrusterTarget.y,
                new Vector(-thrusterTarget.x , thrusterTarget.y, 0)
                        .getAngle(new Vector(0, 1, 0)));
       
        thrusterTarget.z = thrusterTarget.getAngle(new Vector(0, 1, 0));
        changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
        SolarSystem.landingModule.setNextVelocity(SolarSystem.landingModule.getNextVelocity().x, SolarSystem.landingModule.getNextVelocity().y-SolarSystem.landingModule.getNextVelocity().y, SolarSystem.landingModule.getNextVelocity().z);
        thruster.set(thrusterTarget);
        while(tracker>0) {
            SolarSystem.landingModule
                    .setNextVelocity(new Vector(SolarSystem.landingModule.getVelocity().x - sign* acceleration*modifier,
                            SolarSystem.landingModule.getVelocity().y
                        + (thruster.y / SolarSystem.landingModule.getMass())- gravity+(wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y )* PhysicsUtils.STEPSIZE, 0));
           updateCoordinates();//A check if update coordinates is actually all we need 
           tracker--;
           
        }
        
        if ((2 * (Math.sqrt(2 * acceleration*modifier * (distance / 2)) / acceleration*modifier)) < 0.00001) {
            SolarSystem.landingModule.setNextLocation(0, SolarSystem.landingModule.getLocation().y,
                    SolarSystem.landingModule.getLocation().z);
            //SolarSystem.landingModule.setNextVelocity(0,SolarSystem.landingModule.getVelocity().y,0);
                    thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x , -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity*SolarSystem.landingModule.getMass(),
                new Vector(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity*SolarSystem.landingModule.getMass(), 0.0)
                      .getAngle(new Vector(0, 1, 0)));
                changeAngle(thrusterTarget.z - SolarSystem.landingModule.getRotation());
                thruster.set(thrusterTarget);
                updateVelocity();
                updateCoordinates();
                   
        } else {
  
            Controller.alignX(modifier/4);
        }
    }
   /*  private static void descend() throws IOException {
        double scalar = acceleration*(1/intialheight);// have to find value such that the thruster doesnt emit more than it is allowed
        double distance = SolarSystem.landingModule.getLocation().y;
        double originaly= thrusterTarget.y;
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y - scalar*(Math.pow(distance, 2)) , 0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
       /*  for(int i =0; i<3;i++){
        updateVelocity();
        updateCoordinates();}
        while (SolarSystem.landingModule.getLocation().y > 0.01) {
            
            thrusterTarget.set(thrusterTarget.x,thrusterTarget.y + scalar*((Math.pow(SolarSystem.landingModule.getLocation().y, 2))) , 0);
            thrusterTarget.set(thrusterTarget.x, thrusterTarget.y, thrusterTarget.getAngle(new Vector(0, 1, 0)));
            thruster.set(thrusterTarget);
            changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            updateVelocity();
            updateCoordinates();
            if (SolarSystem.landingModule.getLocation().y + (SolarSystem.landingModule.getNextVelocity().y
                    + (thrusterTarget.y/SolarSystem.landingModule.getMass() - (Math.pow(distance, 2)) * scalar)) <= 0) {
                       
                break;
            }
        }
        // either we include landing and checks here, or we make another methodS

    }*/
    public static double findTargetForce(double speed,double time){
     return ((speed-SolarSystem.landingModule.getVelocity().y)/time) * SolarSystem.landingModule.getMass();
    }
    private static double findTargetDistance(double acceleration){
        return Math.pow(SolarSystem.landingModule.getVelocity().y,2)/(2*acceleration);
    }

private static void descend() throws IOException {
        
        double time =20;
        double targetForce = findTargetForce(1,time);

        double scalar = acceleration*(1/intialheight);// have to find value such that the thruster doesnt emit more than it is allowed
        double distance = SolarSystem.landingModule.getLocation().y;
      //  double originaly= thrusterTarget.y;
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+gravity*SolarSystem.landingModule.getMass() - targetForce, 0.0);
       thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        for(int i=0;i<time;i++){
            updateVelocity();
            updateCoordinates();
        }
        
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity*SolarSystem.landingModule.getMass() , 0.0);
       thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
       double limit =findTargetDistance(targetForce/SolarSystem.landingModule.getMass());
        while (SolarSystem.landingModule.getLocation().y > limit +5) {
            updateVelocity();
            updateCoordinates();
            if((-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x!= thruster.x) || (-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y!= thruster.y- gravity*SolarSystem.landingModule.getMass() ) ){
                 thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity*SolarSystem.landingModule.getMass() , 0.0);
       thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            }
        }
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity*SolarSystem.landingModule.getMass() +targetForce , 0.0);
       thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        for(int i=0;i<time;i++){
            updateVelocity();
            updateCoordinates();
             if((-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x!= thruster.x)  ){
                thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity*SolarSystem.landingModule.getMass() +targetForce , 0.0);
       thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());}
              
        }
         time =3;
        targetForce=findTargetForce(-0.0001,time);
        thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x,-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity*SolarSystem.landingModule.getMass() +targetForce , 0.0);
       thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());

       for(int i=0;i<time;i++){
            updateVelocity();
            updateCoordinates();
              
        }

          thrusterTarget.set(-wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).x, -wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y+ gravity*SolarSystem.landingModule.getMass() , 0.0);
       thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
       thruster.set(thrusterTarget);
       changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        while(SolarSystem.landingModule.getLocation().y>0.0001){
           updateVelocity();
            updateCoordinates();
             // System.out.println(SolarSystem.landingModule.getLocation().y + "howw"); 
        }
        System.out.println(SolarSystem.landingModule.getLocation().y +"balls");
        if(confirmland()){
            System.out.println("gj");
        }
    }

           /*  stabilize();
           thrusterTarget.set(thrusterTarget.x, wind.getForceBasedOnDistance(SolarSystem.landingModule.getLocation().y).y +gravity*SolarSystem.landingModule.getMass(), thrusterTarget.z);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
            updateVelocity();
            updateCoordinates();
            
                stabilize();
        double time =500;
        double neededAcceleration= 2*(SolarSystem.landingModule.getLocation().y/2)/Math.pow(time, 2);
        //double neededAcceleration = 2 * ((SolarSystem.landingModule.getLocation().y / 2) - SolarSystem.landingModule.getLocation().y * time) / Math.pow(time, 2); 
        thrusterTarget.set(thrusterTarget.x, gravity-neededAcceleration*SolarSystem.landingModule.getMass() , 0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        for(int i=0;i<time/2;i++){
            
            updateVelocity();
            updateCoordinates();
        }
        thrusterTarget.set(thrusterTarget.x, +neededAcceleration*SolarSystem.landingModule.getMass()+gravity , 0);
        thrusterTarget.set(thrusterTarget.x, thrusterTarget.y , thrusterTarget.getAngle(new Vector(0, 1, 0)));
        thruster.set(thrusterTarget);
        changeAngle(thruster.z - SolarSystem.landingModule.getRotation());
        for(int i=0;i<time/2;i++){
            
            updateVelocity();
            updateCoordinates();
        }
        if (SolarSystem.landingModule.getLocation().y + (SolarSystem.landingModule.getNextVelocity().y
                    + (thrusterTarget.y/SolarSystem.landingModule.getMass() - (Math.pow(distance, 2)) * scalar)) <= 0) {
                       System.out.println("HI");
               // break;
            }
        // either we include landing and checks here, or we make another methodS
*/
    
   /*  private static void landCorrrecter() throws IOException {
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
                timey = 2 * (Math.sqrt(SolarSystem.landingModule.getLocation().y * acceleration) / acceleration);
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
    }*/

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
        if(Math.abs(SolarSystem.landingModule.getRotation())>20000){//check angle bound
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
       while(SolarSystem.landingModule.getLocation().y>targetHeight*(1.0/3.0)){
        double xComponenet = -1*SolarSystem.landingModule.getLocation().x;
       double yComponent = -1*SolarSystem.landingModule.getLocation().y;
       
       if(Math.abs(xComponenet)>Math.abs(yComponent)){
        yComponent= (yComponent/xComponenet)*acceleration;
        xComponenet=acceleration;
       }else{
        xComponenet=(-xComponenet/yComponent)*acceleration;
        yComponent=-acceleration;
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

