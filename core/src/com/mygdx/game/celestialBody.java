package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

import java.util.jar.Attributes.Name;

public class celestialBody{
    public final String name;
    public final double mass;   // kg
    public final double radius; // meters
    public Vector3 location; // meters
    public Vector3 velocity;


    celestialBody(String name, double mass, double radius, Vector3 location, Vector3 velocity){
        this.name = name;
        this.mass = mass;
        this.radius=radius;
        this.location=location;
        this.velocity=velocity;

    }

    public Vector3 getLocation(){
        return location;
    }
    public Vector3 getVelocity(){
        return velocity;
    }
    public double getMass(){
        return mass;
    }
    public double getRadius(){
        return radius;
    }
    public String getName(){
        return name;
    }


}