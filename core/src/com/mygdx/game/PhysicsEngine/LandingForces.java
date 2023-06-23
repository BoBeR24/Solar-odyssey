package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Objects.Vector;

public class LandingForces {

    private final static double titanGravity = 1.352 * Math.pow(10, -3);

    private static Wind wind;

    public static double calculateGravity(){
        return titanGravity / SolarSystem.landingModule.getMass();
    }

    public static Vector calculateWind(double distance){
        wind = Wind.getWind();
        return wind.getForceBasedOnDistance(distance);
    }
    
}
