package com.mygdx.game;

//import com.badlogic.gdx.math.Vector3;

import java.util.jar.Attributes.Name;

public class celestialBody{
    public final String name;
    public final double mass;   // kg
    public final double radius; // meters
    public Vector location; // meters
    public Vector velocity;


    celestialBody(String name, double mass, double radius, Vector location, Vector velocity){
        this.name = name;
        this.mass = mass;
        this.radius=radius;
        this.location=location;
        this.velocity=velocity;

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
    public String getName(){
        return name;
    }


}