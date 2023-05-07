package com.mygdx.game;

import static java.lang.Math.*;

/** Implementation of Vector class for double data type */
public class Vector {
    public double x;
    public double y;
    public double z;

    public Vector(double x, double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Vector(Vector vector){
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    /**
     * Updates current values with values from input
     **/
    public Vector set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public Vector set(Vector vector){
        return set(vector.x, vector.y, vector.z);
    }

    /**
     * Applies mathematical operation of summing two vectors
     **/
    public Vector add(Vector b){
        return new Vector((this.x + b.x), (this.y + b.y), (this.z + b.z));
    }

    public Vector add(double b){
        return add(new Vector(b, b, b));
    }


    /**
     * Applies mathematical operation of subtracting two vectors
     **/
    public Vector subtract(Vector b){
        return new Vector((this.x - b.x), (this.y - b.y), (this.z - b.z));
    }

    public Vector subtract(double b){
        return subtract(new Vector(b, b, b));
    }

    /**
     * Applies mathematical operation of the vector and scalar value
     **/
    public Vector multiply(double scalingFactor){
        return new Vector((this.x * scalingFactor), (this.y * scalingFactor), (this.z * scalingFactor)); // - both changes values and returns them
    }

    /**
     * Applies mathematical operation of multiplying two vectors
    * */
    public Vector multiply(Vector vector) {
           return multiply(vector.x);
    }


    /**
     * Calculates magnitude of the current vector
     * */
    public double magnitude(){
        return sqrt((x * x) + (y * y) + (z * z));
    }

    public void rotate(double angle, char axis) {
        if (axis == 'z') {
            double new_X = x * cos(angle) - y * sin(angle);
            double new_Y = x * sin(angle) + y * cos(angle);
            this.set(new_X, new_Y, this.z);
        }
        else if (axis == 'x') {
            double new_Y = y * cos(angle) - z * sin(angle);
            double new_Z = y * sin(angle) + z * cos(angle);
            this.set(this.x, new_Y, new_Z);
        }
        else if (axis == 'y') {
            double new_X = x * cos(angle) + z * sin(angle);
            double new_Z = (-1 * x) * sin(angle) + z * cos(angle);
            this.set(new_X, this.y, new_Z);
        }
    }

    /**
     * Overrides toString function to print vector
     **/
    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
