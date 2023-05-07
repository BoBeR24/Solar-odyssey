package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

//import java.beans.VetoableChangeListenerProxy;
//import java.util.ArrayList;
//import java.util.Objects;
// faire method de celestial body qui va prendre en param le vecteur avnt et celui ci doit etre store by value dans un instance et each time celui ci doit etre remis a jour.
/**
 * Class representing all bodies in simulated system
 * */
public class celestialBody implements Body{
//    private final String name; // name of the body
    private final int id; // id which represents position in the list of bodies
    private Color color; // color of the body
    private int width; // width of the image of the body
    private int height; // height of the image of the body
   

    celestialBody(int index){
//        this.name = name;

//        this.id = SystemProperties.entities.get(name);
       
        this.id = index;

        this.color = Color.WHITE; // default color

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
    public void setLocation(double x, double y, double z, int a) {
        SystemProperties.globalCoordinates[a][id].x = x;  
        SystemProperties.globalCoordinates[a][id].y = y;
        SystemProperties.globalCoordinates[a][id].z = z;
    }

    public void setLocation(Vector vector, int a) {
        this.setLocation(vector.x, vector.y, vector.z, a);
    }

    @Override
    public void setVelocity(double x, double y, double z, int a) {
         SystemProperties.globalVelocities[a][id].x = x;
         SystemProperties.globalVelocities[a][id].y = y;
         SystemProperties.globalVelocities[a][id].z = z;
    }

    public void setVelocity(Vector vector, int a) {
        this.setVelocity(vector.x, vector.y, vector.z, a);
    }

//    public String getName(){
//        return name;
//    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Vector getLocation(int a){
        return SystemProperties.globalCoordinates[a][id];
    }
    @Override
    // this method gives the velocity at a given time a, and a is from 0 to 3
    public Vector getVelocity(int a){
        return SystemProperties.globalVelocities[a][id];
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