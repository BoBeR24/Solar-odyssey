package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.jar.Attributes.Name;

public class celestialBody{
    private final String name;
    private final double mass;   // kg
    private final double radius; // meters
    private final int id;
    private Vector location; // meters
    private Vector velocity;
    private Texture texture;


    celestialBody(String name){
        this.name = name;
        this.id = SystemProperties.entities.get(name);
        this.mass = SystemProperties.masses[id];
        this.radius = SystemProperties.radiuses[id];
        this.location = SystemProperties.coordinates[id];
        this.velocity = SystemProperties.velocities[id];

        this.texture = new Texture(Gdx.files.internal(name + ".png"));

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