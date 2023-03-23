package com.mygdx.game;

public class Probe implements Body{
    private String name;
    private double mass;
    private Vector location;
    private Vector velocity;
    private Vector pStart; // To track the starting position of the probe
    private Vector vStart; // To track the startin velocity of the probe
    private double distanceToTitan; // closest the probe has gotten to titan
    

    Probe() {
        this.name = "Probe";
        this.mass = 50000;
        this.location = SystemProperties.coordinates[3].add(new Vector(SystemProperties.radii[3], 0, 0)); // default position
        this.velocity = SystemProperties.velocities[3]; // default velocity

        this.pStart = new Vector(this.location);
        this.vStart = new Vector(this.velocity);
    }

    @Override
    public void setLocation(double x, double y, double z){
        this.location = new Vector(x, y, z);
    }

    public void setLocation(Vector vector){
        this.setLocation(vector.x, vector.y, vector.z);
    }

    public void setVelocity(double x, double y, double z){
        this.velocity = new Vector(x, y, z);
    }

    public void setVelocity(Vector vector){
        this.setVelocity(vector.x, vector.y, vector.z);
    }


    
    public Vector getLocation(){
        return this.location;
    }

    public Vector getVelocity(){
        return this.velocity;
    }

    public double getMass(){
        return this.mass;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setPStart(Vector p){
        this.pStart = p;
    }

    public Vector getPStart(){
        return pStart;
    }

    public void setVSRART(Vector v){
        this.vStart = v;
    }

    public Vector getVStart(){
        return this.vStart;
    }

    public double getdistanceToTitan(){
        return this.distanceToTitan;
    }

    public void setDistanceToTitan(double distance){
        this.distanceToTitan = distance;
    }
}
