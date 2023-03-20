package com.mygdx.game;

import static java.lang.Math.sqrt;

public class Vector {
    double x;
    double y;
    double z;
    public Vector(double x, double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public void Set(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Vector Add(Vector a, Vector b){
        return new Vector((a.getX()+b.getX()),(a.getY()+b.getY()),(a.getZ()+b.getZ()));
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public double getZ(){return z;}

    public double getLength(){
        return sqrt((x*x)+(y*y)+(z*z));
    }
}
