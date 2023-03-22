package com.mygdx.game;

public class Probe extends celestialBody{

    private double mass;
    private Vector location;
    private Vector velocity;
    private String name;

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

}
