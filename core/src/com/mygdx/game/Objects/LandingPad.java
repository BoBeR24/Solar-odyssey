package com.mygdx.game.Objects;

public class LandingPad implements Body{
    private Vector velocity;
    private Vector location;
    private Vector nextVelocity;
    private Vector nextLocation;
    private final double mass = 2000;
    private final int id = 0;
    private int width;
    private int height;

    public LandingPad() {
        this.location = new Vector(0.0, 0.0, 0.0);
        this.velocity = new Vector(0.0, 0.0, 0.0);

        setHeight(40);
        setWidth(100);
    }

    /** As landing pad doesn't have any dynamic properties clone method here is more like of a placeholder
     *
     * */
    @Override
    public LandingPad clone(){

        return new LandingPad();
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int getId() {
        return id;
    }
}
