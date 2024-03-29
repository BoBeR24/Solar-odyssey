package com.mygdx.game.Objects;

/** Module which is used to land on Titan
 * */
public class LandingModule implements Body{
    private Vector velocity;
    private Vector location;
    private Vector nextVelocity;
    private Vector nextLocation;
    private double rotation; // rotation of the module relative to y-axis
    private final double mass = 2000;
    private final int id = 1;
    private float width;
    private float height;

    public LandingModule(Vector initialLocation, Vector initialVelocity, double rotation) {
        this.location = initialLocation;
        this.velocity = initialVelocity;

        this.nextLocation = initialLocation;
        this.nextVelocity = initialVelocity;

        this.rotation = rotation;

        // these scales do not represent real size of object, they are only used for gui representation
        setHeight(8f);
        setWidth(8f);
    }

    /** !!!This clone method doesn't do a full copy, it only transfers essential characteristics,
     *  such as location and velocity
     * */
    @Override
    public LandingModule clone(){
        LandingModule clonedRocket = new LandingModule(new Vector(0.0, 0.0, 0.0), new Vector(0.0, 0.0, 0.0), this.rotation);

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

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
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

    public double getRotation() {
        return this.rotation;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public int getId() {
        return id;
    }
}
