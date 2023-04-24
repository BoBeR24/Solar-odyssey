package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

import java.util.Objects;

/**
 * Class representing all bodies in simulated system
 * */
public class celestialBody implements Body{
    private final String name; // name of the body
    private final int id; // id which represents position in the list of bodies
    private Color color; // color of the body
    private int width; // width of the image of the body
    private int height; // height of the image of the body


    celestialBody(String name){
        this.name = name;

        this.id = SystemProperties.entities.get(name);

        this.color = Color.WHITE; // default color

        if (Objects.equals(this.name, "Sun")) {
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
        SystemProperties.coordinates[id].x = x;
        SystemProperties.coordinates[id].y = y;
        SystemProperties.coordinates[id].z = z;
    }

    @Override
    public void setVelocity(double x, double y, double z) {
         SystemProperties.velocities[id].x = x;
         SystemProperties.velocities[id].y = y;
         SystemProperties.velocities[id].z = z;
    }

    @Override
    public String getName(){
        return name;
    }

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
    public double getRadius(){
        return SystemProperties.radii[id];
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