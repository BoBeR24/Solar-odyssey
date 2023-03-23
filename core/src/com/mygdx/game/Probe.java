package com.mygdx.game;

public class Probe extends celestialBody{

    private double mass;
    private Vector location;
    private Vector velocity;
    private Vector pStart; // To track the starting position of the probe
    private Vector vStart; // To track the startin velocity of the probe
    private double distanceToTitan; // closest the probe has gotten to titan
    

    Probe(String name) {
        super(name);
        mass = 50000;
    }

    public void setLocation(double x, double y, double z){
        this.location = new Vector(x, y, z);
    }

    public void setVelocity(double x, double y, double z){
        this.velocity = new Vector(x, y, z);
    }
    
    public Vector getLocation(){
        return location;
    }

    public Vector getVelocity(){
        return velocity;
    }

    public double getMass(){
        return mass;
    }

    public void setPSTART(Vector p){
        this.pStart = p;
    }

    public Vector getPStrart(){
        return pStart;
    }

    public void setVSRART(Vector v){
        this.vStart = v;
    }

    public Vector getVStart(){
        return vStart;
    }

    public double getdistanceToTitan(){
        return distanceToTitan;
    }

    public void setDistanceToTitan(double distance){
        this.distanceToTitan = distance;
    }
}
