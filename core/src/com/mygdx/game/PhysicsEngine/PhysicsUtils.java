package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SystemProperties;

import java.util.ArrayList;

/**
 * Class with helping physical related methods and constants used in calculations
 * */
public class PhysicsUtils{
    //Gravitational constant expressed in cubic kilometers per kilogram per second squared
    public final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    //Nasa Coordinates after 1 year
    private final static Vector NASA_Earth = new Vector(-146525538.264264, -29597910.637783, 2927.15182555466);


    public static double relativeError(Vector Location){
        Vector Error = Location.subtract(NASA_Earth);
        Error.x = Math.abs(Error.x * (1.0/ NASA_Earth.x));
        Error.y = Math.abs(Error.y * (1.0/ NASA_Earth.y));
        Error.z = Math.abs(Error.z * (1.0/NASA_Earth.z));

        Error.multiply(100);

        double averageError = (Error.x + Error.y + Error.z)/3;
        return averageError;
    }
}
