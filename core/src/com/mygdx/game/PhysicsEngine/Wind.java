package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;
import java.lang.Math.*;

public class Wind {

    final double SHIP_DIAMETER = 7; // in meters
    final double ATMOSPHARE_DENSITY = 1.240512; // in kg/m^2 (98,4%*1,25+1,6%*0,657=1,240512 NITROGEN & ETHANE)  but maybe just use 1,5*Earth air density 
    final double ZERO_BOUND = 0.1;
    final double FIRST_BOUND = 7;
    final double SECOND_BOUND = 60;
    final double THIRD_BOUND = 120;
    private Vector forceBoundZero;
    private Vector forceBoundOne;
    private Vector forceBoundTwo;
    private Vector forceBoundThree;
    private int windDirection; // 1 for from left to right, 2 for from right to left
    private int angle; // +- 15 degrees
    private Vector wind;
    private static Wind Wind; 
    
    //TODO: do the constructor
    private Wind(){
        randomAngle();
        randomWindDirection();

        double velocity = randomVelocity(ZERO_BOUND);
        double windPressure = windPressure(velocity);
        double windForce = windForce(windPressure, shipArea());
        wind = newWind(windForce);
        wind = vectorDirectionRotation(wind);
        wind = vectorAngleRotation(wind);

    }
    /**
     * 
     * @param distanceToTitan in km
     * @return vector of force in N (z coordinate is always 0)
     */
    public static Wind getWind(){
        if(Wind  == null){
            Wind Wind = new Wind();
        }
        return Wind;
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

    /**
     * rotation vector of wind based on the angle
     * @param vector of wind with only x coordinate being =! 0
     * @return rotated vetor based on the angle
     */
    private Vector vectorAngleRotation(Vector vector){
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

    /**
     * changing the direction of wind if the wind is blowing from left to right
     * @param Vector with values =! 0 only on x coordinate
     * @return vector 
     */
    private Vector vectorDirectionRotation(Vector vector){
        if (this.windDirection == 2){
            vector.x = vector.x * (-1);
        }
        return vector;
    }
    

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * velocity depends on the height we are on, the higher we are the stronger the wind can be
     * @param distanceToTitan
     * @return velocity of wind in x-axis, from 0 to the upper bound based on the distance
     */
    private double randomVelocity(double distanceToTitan){
        if (distanceToTitan == ZERO_BOUND){
            return 0;
        } else if (distanceToTitan == FIRST_BOUND){
            return getRandomNumber(0, 9);
        } else if(distanceToTitan == SECOND_BOUND){
            return getRandomNumber(0, 17);
        } else if (distanceToTitan == THIRD_BOUND){
            return getRandomNumber(0, 120);
        } else return 0;
    }

    /**
     * randomise the direction of wind (1 for wind blowind from left to right, 2 for wind blowing from right to left)
     */
    private void randomWindDirection(){
        this.windDirection = getRandomNumber(1, 2);
    }
    /**
     * creating new wind vector with defined velocity in x-axis
     * @param windForce
     * @return 
     * */
    private Vector newWind(double windForce){
        Vector vector = new Vector(0.0, 0.0, 0.0);
        vector.x = windForce;
        return vector;
    }

    // Getter for windDirection
    public int getWindDirection() {
        return windDirection;
    }

    // Setter for windDirection
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    // Getter for angle
    public int getAngle() {
        return angle;
    }

    // Setter for angle
    public void setAngle(int angle) {
        this.angle = angle;
    }


    // Setter for wind
    public void setWind(Vector wind) {
        this.wind = wind;
    }

    //TODO: getter and setter for forces on particular bounds


    //TODO: method that takes distance to titan and returns particular force of wind at that distance

    
}
