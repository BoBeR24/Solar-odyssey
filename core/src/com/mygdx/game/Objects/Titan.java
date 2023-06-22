package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;

/**
 * Class representing all bodies in simulated system
 * */
public class Titan implements Body {
    //    private final String name; // name of the body
    private final int id = 1; // id which represents position in the list of bodies
    private Vector location;
    private Vector nextLocation; // location to which planet will be moved in the next state of the system
    private Vector velocity;
    private Vector nextVelocity; // velocity of the current object in the next state of the system
    private final int radius;
    private final double mass;
    private Color color; // color of the body
    private int width; // width of the image of the body
    private int height; // height of the image of the body

/** class for Titan used during the landing(as properties of Titan during the landing differ a bit from flight simulation due
 * to some simplifications in calculations)
 * */
    public Titan(){
        this.color = Color.WHITE; // default color

        this.radius = SystemProperties.radii[id];

        this.location = new Vector(new Vector(0.0, -this.radius, 0.0));
        this.velocity = new Vector(new Vector(0.0, 0.0, 0.0));

        // let next states initially be same as current(they will be changed anyway)
        this.nextLocation = new Vector(this.location);
        this.nextVelocity = new Vector(this.velocity);


        this.mass = SystemProperties.masses[id];

        this.width = SystemProperties.radii[id] * 2; // default width and height
        this.height = SystemProperties.radii[id] * 2;
    }


    /** As Titan doesn't have any dynamic properties in landing scene(so we consider it as a static object)
     *  clone method here is more like of a placeholder
     *
     * */
    @Override
    public Titan clone() {

        return new Titan();
    }

    @Override
    public void update() {
        this.setLocation(this.nextLocation);
        this.setVelocity(this.nextVelocity);
    }

    @Override
    public void setLocation(double x, double y, double z) {
        this.location.x = x;
        this.location.y = y;
        this.location.z = z;
    }

    public void setLocation(Vector vector) {
        this.setLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.z = z;
    }

    public void setVelocity(Vector vector) {
        this.setVelocity(vector.x, vector.y, vector.z);
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getId() {
        return id;
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

    public double getRadius(){
        return this.radius;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }



}
