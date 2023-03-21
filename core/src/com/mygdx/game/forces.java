package com.mygdx.game;
//import com.mygdx.game.vector;

// Calculates forces between a Planet and Spacecraft

public class forces{

    //Still have to change
    public final static double gravitionalconstant = 6.0;

    public static Vector actingforces(celestialBody Planet, celestialBody Spacecraft){

        double scalingfactor = gravitionalconstant*Planet.getMass()*Spacecraft.getMass()*(-1);

        Vector Planetvector = Planet.getLocation();
        Vector Spacecraftvector = Spacecraft.getLocation();

        Vector force = Spacecraftvector.subtract(Planetvector);

        double magnitude = Math.pow(force.getLength(), 3);

        force.multiply(scalingfactor/magnitude);

        return force;
    }

}
