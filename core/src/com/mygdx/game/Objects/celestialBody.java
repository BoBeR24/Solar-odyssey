package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;

/**
 * Class representing all bodies in simulated system
 * */
public class celestialBody implements Body {
//    private final String name; // name of the body
    private final int id; // id which represents position in the list of bodies
    private Vector location;
    private Vector nextLocation; // location to which planet will be moved in the next state of the system
    private Vector velocity;
    private Vector nextVelocity; // velocity of the current object in the next state of the system
    private double radius;
    private double mass;
    private Color color; // color of the body
    private int width; // width of the image of the body
    private int height; // height of the image of the body


    public celestialBody(int index){
        this.id = index;

        this.color = Color.WHITE; // default color

        this.location = new Vector(SystemProperties.initCoordinates[id]);
        this.velocity = new Vector(SystemProperties.initVelocities[id]);

        // let next states initially be same as current(they will be changed anyway)
        this.nextLocation = new Vector(this.location);
        this.nextVelocity = new Vector(this.velocity);

        this.radius = SystemProperties.radii[id];

        this.mass = SystemProperties.masses[id];

        // if object id refers to the Sun scaling factor differs, to avoid sun being too big
        if (this.id == 0) {
            this.width = (int) SystemProperties.radii[0] / (SolarSystem.SIZE_FACTOR * 8); // default width and height
            this.height = (int) SystemProperties.radii[0] / (SolarSystem.SIZE_FACTOR * 8);

        }
        else {
            this.width = (int) SystemProperties.radii[id] / SolarSystem.SIZE_FACTOR; // default width and height
            this.height = (int) SystemProperties.radii[id] / SolarSystem.SIZE_FACTOR;
        }
    }


    // example of cloning Earth:
    // Body new_body = SolarSystem.planets.get(SystemProperties.EARTH).clone();
    @Override
    public celestialBody clone() {
        celestialBody cloned_body = new celestialBody(this.id);

        cloned_body.setLocation(new Vector(this.location));
        cloned_body.setVelocity(new Vector(this.velocity));

        cloned_body.setNextLocation(new Vector(this.nextLocation));
        cloned_body.setNextVelocity(new Vector(this.nextVelocity));

        return cloned_body;
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