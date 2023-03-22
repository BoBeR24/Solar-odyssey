package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

import java.util.Objects;

/**
 * Class representing all bodies in simulated system
 * */
public class celestialBody{
    private final String name; // name of the body
    private final double mass;   // kg
    private final int radius; // meters
    private final int id;
    private Vector location; // meters
    private Vector velocity; // km/s
    private Color color;
    private int width; // width of the image of the body
    private int height; // height of the image of the body


    celestialBody(String name){
        this.name = name;

        this.id = SystemProperties.entities.get(name);

        this.mass = SystemProperties.masses[id];
        this.radius = SystemProperties.radii[id];
        this.location = SystemProperties.coordinates[id];
        this.velocity = SystemProperties.velocities[id];

        this.color = Color.WHITE; // default color

        if (Objects.equals(this.name, "Sun")) {
            this.width = this.radius / (SolarSystem.SIZE_FACTOR * 8); // default width and height
            this.height = this.radius / (SolarSystem.SIZE_FACTOR * 8);

        }
        else {
            this.width = this.radius / SolarSystem.SIZE_FACTOR; // default width and height
            this.height = this.radius / SolarSystem.SIZE_FACTOR;
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

    public void setLocation(double x, double y, double z) {
        SystemProperties.coordinates[id].x = x;
        SystemProperties.coordinates[id].y = y;
        SystemProperties.coordinates[id].z = z;
    }

    public void setVelocity(double x, double y, double z) {
         SystemProperties.velocities[id].x = x;
         SystemProperties.velocities[id].y = y;
         SystemProperties.velocities[id].z = z;
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
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
    public double getRadius(){
        return radius;
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