package com.mygdx.game;

import java.util.ArrayList;

/**
 * Contains all bodies which are currently presented in the simulation. Also contains size and distance scaling factors
 * */
public class SolarSystem {
    public static ArrayList<celestialBody> bodies = new ArrayList<>();
//    public final static int scaleFactor = 407734; // pre-calculated scaling factor
    public final static int DIST_FACTOR = 300000; // pre-calculated distance scaling factor
    public static int SIZE_FACTOR = 200; // pre-calculated scale scaling factor


}
