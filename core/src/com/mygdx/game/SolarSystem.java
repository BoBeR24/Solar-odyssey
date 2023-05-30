package com.mygdx.game;

import java.util.ArrayList;

/**
 * Contains all bodies which are currently presented in the simulation. Also contains size and distance scaling factors
 * */
public class SolarSystem {
    public static ArrayList<Body> bodies = new ArrayList<>();
    public static Probe probe;
    public final static int DIST_FACTOR = 300_000; // pre-calculated distance scaling factor
    public static int SIZE_FACTOR = 2000; // pre-calculated scale scaling factor

}
