package com.mygdx.game.Properties;

import com.mygdx.game.Objects.Vector;

public class SystemProperties {
    // BELOW GO CONSTANTS WHICH CONTAIN CORRESPONDING TO EACH PLANET INDEX
    final public static int SUN = 0;
    final public static int MERCURY = 1;
    final public static int VENUS = 2;
    final public static int EARTH = 3;
    final public static int MOON = 4;
    final public static int MARS = 5;
    final public static int JUPITER = 6;
    final public static int SATURN = 7;
    final public static int TITAN = 8;
    final public static int NEPTUNE = 9;
    final public static int URANUS = 10;
    final public static int PROBE = 11;
    final public static int ROCKET = 12;

     final public static Vector[] initCoordinates = new Vector[11];
     final public static Vector[] initVelocities= new Vector[11];

     final public static double[] masses = new double[11];
     final public static double[] radii = new double[11];
}
