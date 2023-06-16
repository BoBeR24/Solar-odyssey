package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;
import java.lang.Math.*;

public class Wind {

    final double SHIP_DIAMETER = 7; // in meters
    final double ATMOSPHARE_DENSITY = 1.240512; // in kg/m^2 (98,4%*1,25+1,6%*0,657=1,240512 NITROGEN & ETHANE)  but maybe just use 1,5*Earth air density 
    int windDirection; // 1 for from left to right, 2 for from right to left
    int angle; // +- 15 degrees 
    
    public Wind(){
    }
    /**
     * 
     * @param distanceToTitan in km
     * @return vector of force in N (z coordinate is always 0)
     */
    public Vector generateWind(double distanceToTitan){
        double velocity = randomVelocity(distanceToTitan);
        double windPressure = windPressure(velocity);
        double windForce = windForce(windPressure, shipArea());
        Vector wind = newWind(windForce);
        wind = vectorDirectionRotation(wind);
        wind = vectorAngleRotation(wind);
        return wind;
    }
    /**
     * 4*PI*R^2 but we say that wind blows always on the half of the area of the spaceship 
     * @return half of the ship area in m^2
     */
    private double shipArea(){
        return 4 * Math.PI * Math.pow(SHIP_DIAMETER, 2) / 2;
    }

    /**
     * 0.5 * atmosphare density * V^2
     * @return wind pressure in N/m^2
     */
    private double windPressure(double windTotalVelocity){
        return 0.5 * ATMOSPHARE_DENSITY * Math.pow(windTotalVelocity, 2);
    }

    private double windForce(double windPressure, double shipArea){
        return windPressure * shipArea;
    }

    private Vector vectorAngleRotation(Vector vector){
        randomAngle();
        Vector newVector = new Vector(0.0, 0.0, 0.0);
        // double newX = Math.cos(this.angle)*vector.x - Math.sin(this.angle)*vector.y
        // double newY = Math.sin(this.angle)*vector.x - Math.cos(this.angle)*vector.y;
        double newX = Math.cos(this.angle)*vector.x;
        double newY = Math.sin(this.angle)*vector.x;
        newVector.x = newX;
        newVector.y = newY;
        return newVector;
    }

    private void randomAngle(){
        this.angle = getRandomNumber(-15, 15);
    }

    private Vector vectorDirectionRotation(Vector vector){
        randomWindDirection();
        if (this.windDirection == 2){
            vector.x = vector.x * (-1);
        }
        return vector;
    }
    

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private double randomVelocity(double distanceToTitan){
        if (distanceToTitan < 7){
            return getRandomNumber(0, 9);
        } else if(distanceToTitan < 60){
            return getRandomNumber(0, 17);
        } else if (distanceToTitan < 120){
            return getRandomNumber(0, 120);
        } else return 0;
    }

    private void randomWindDirection(){
        this.windDirection = getRandomNumber(1, 2);
    }

    private Vector newWind(double windForce){
        Vector vector = new Vector(0.0, 0.0, 0.0);
        vector.x = windForce;
        return vector;
    }

    
}
