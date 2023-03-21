package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Class representing all bodies in simulated system
 * */
public class celestialBody{
    private final String name; // name of the body
    private final double mass;   // kg
    private final double radius; // meters
    private final int id;
    private Vector location; // meters
    private Vector velocity; // km/s
    private Texture texture; // sprite of the body


    celestialBody(String name){
        this.name = name;

        this.id = SystemProperties.entities.get(name);
        this.mass = SystemProperties.masses[id];
        this.radius = SystemProperties.radii[id];
        this.location = SystemProperties.coordinates[id];
        this.velocity = SystemProperties.velocities[id];
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

    public Texture getTexture() {
        return texture;
    }
}