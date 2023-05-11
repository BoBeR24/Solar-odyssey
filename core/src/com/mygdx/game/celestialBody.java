package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Class representing all bodies in simulated system
 * */
public class celestialBody implements Body{
//    private final String name; // name of the body
    private final int id; // id which represents position in the list of bodies
    private Vector location;
    private Vector velocity;
    private int radius;
    private Color color; // color of the body
    private int width; // width of the image of the body
    private int height; // height of the image of the body


    celestialBody(int index){
//        this.name = name;

//        this.id = SystemProperties.entities.get(name);

        this.id = index;

        this.color = Color.WHITE; // default color

        this.location = new Vector(SystemProperties.coordinates[id]);
        this.velocity = new Vector(SystemProperties.velocities[id]);

        this.radius = SystemProperties.radii[id];

        // if object id refers to the Sun scaling factor differs, otherwise sun is too big
        if (this.id == 0) {
            this.width = SystemProperties.radii[0] / (SolarSystem.SIZE_FACTOR * 8); // default width and height
            this.height = SystemProperties.radii[0] / (SolarSystem.SIZE_FACTOR * 8);

        }
        else {
            this.width = SystemProperties.radii[id] / SolarSystem.SIZE_FACTOR; // default width and height
            this.height = SystemProperties.radii[id] / SolarSystem.SIZE_FACTOR;
        }
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
    public int getId() {
        return id;
    }

    @Override
    public Vector getLocation(){
        return SystemProperties.coordinates[id];
    }

    @Override
    public Vector getVelocity(){
        return SystemProperties.velocities[id];
    }

    @Override
    public double getMass(){
        return SystemProperties.masses[id];
    }

    @Override
    public celestialBody clone() {
        celestialBody cloned_body = new celestialBody(this.id);

        cloned_body.setLocation(new Vector(this.location));
        cloned_body.setVelocity(new Vector(this.velocity));

        return cloned_body;
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