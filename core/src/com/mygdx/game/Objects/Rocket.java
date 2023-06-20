package com.mygdx.game.Objects;

public class Rocket implements Body{
    private Vector velocity;
    private Vector location;
    private Vector nextVelocity;
    private Vector nextLocation;
    private final int mass;
    private final int id = 12;

    public Rocket(Vector initialLocation, Vector initialVelocity, int mass) {
        this.location = initialLocation;
        this.velocity = initialVelocity;
        this.mass = mass;

        this.nextLocation = initialLocation;
        this.nextVelocity = initialVelocity;
    }

    /** !!!This clone method doesn't do a full copy, it only transfers essential characteristics,
     *  such as location and velocity
     * */
    @Override
    public Rocket clone(){
        Rocket clonedRocket = new Rocket(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0), this.mass);

        clonedRocket.setLocation(new Vector(this.location));
        clonedRocket.setVelocity(new Vector(this.velocity));

        clonedRocket.setNextLocation(new Vector(this.nextLocation));
        clonedRocket.setNextVelocity(new Vector(this.nextVelocity));

        return clonedRocket;
    }

    @Override
    public void update() {
        this.setLocation(this.nextLocation);
        this.setVelocity(this.nextVelocity);
    }

    @Override
    public void setLocation(double x, double y, double z){
        this.location.x = x;
        this.location.y = y;
        this.location.z = z;
    }

    public void setLocation(Vector vector){
        this.setLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public void setVelocity(double x, double y, double z){
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.z = z;
    }

    @Override
    public void setNextLocation(double x, double y, double z) {
        this.nextLocation.x = x;
        this.nextLocation.y = y;
        this.nextLocation.z = z;
    }

    public void setNextLocation(Vector vector) {
        this.setNextLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public void setNextVelocity(double x, double y, double z) {
        this.nextVelocity.x = x;
        this.nextVelocity.y = y;
        this.nextVelocity.z = z;
    }

    public void setNextVelocity(Vector vector) {
        this.setNextVelocity(vector.x, vector.y, vector.z);
    }

    public void setVelocity(Vector vector){
        this.setVelocity(vector.x, vector.y, vector.z);
    }

    @Override
    public Vector getLocation(){
        return this.location;
    }

    @Override
    public Vector getVelocity(){
        return this.velocity;
    }

    @Override
    public Vector getNextLocation() {
        return this.nextLocation;
    }

    @Override
    public Vector getNextVelocity() {
        return this.nextVelocity;
    }

    @Override
    public double getMass(){
        return this.mass;
    }

    @Override
    public int getId() {
        return id;
    }
}
