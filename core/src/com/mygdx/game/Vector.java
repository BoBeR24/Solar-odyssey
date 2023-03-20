package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

import static java.lang.Math.sqrt;

/** Implementation of Vector class for double data type */
public class Vector {
    double x;
    double y;
    double z;

    public Vector(double x, double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * Updates current values with values from input
     **/
    public Vector set(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;

        return this;
    }

    /**
     * Applies mathematical operation of summing two vectors
     **/
    public Vector add(Vector b){
        return set((this.x + b.x), (this.y + b.y), (this.z + b.z));
    }

    /**
     * Applies mathematical operation of subtracting two vectors
     **/
    public Vector subtract(Vector b){
        return set((this.x - b.x), (this.y - b.y), (this.z - b.z));
    }

    /**
     * Applies mathematical operation of multiplying two vectors
     **/
    public Vector multiply(double scalingfactor){
        return set((this.x * scalingfactor), (this.y * scalingfactor), (this.z * scalingfactor));
    }


    /**
     * Overrides toString function to print vector
     **/
    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    public double getLength(){
        return sqrt((x*x)+(y*y)+(z*z));
    }
}
