package com.mygdx.game;
//import com.mygdx.game.vector;

// Calculates forces between a Planet and Spacecraft

public class forces{

    //Gravitational constant expressed in cubic kilometers per kilogram per second squared
    public final static double gravitionalconstant = 6.6743 * Math.pow(10, -20);

    public static Vector actingforces(celestialBody Planet, celestialBody Spacecraft){

        Vector Planetvector = Planet.getLocation();
        Vector Spacecraftvector = Spacecraft.getLocation();

        Vector force = Spacecraftvector.subtract(Planetvector);

        double magnitude = Math.pow(force.getLength(), 2);

        double scalingfactor = (-1)*gravitionalconstant*((Planet.getMass()*Spacecraft.getMass())/magnitude);

        force.multiply(1/force.getLength());

        force.multiply(scalingfactor);

        return force;
    }

}
