package com.mygdx.game.Properties;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.LandingModule;

import java.util.ArrayList;

/**
 * Contains all bodies which are currently presented in the simulation. Also contains size and distance scaling factors
 * */
public class SolarSystem {
    public static ArrayList<Body> bodies = new ArrayList<>();
    public static Probe probe;
    public static LandingModule landingModule;
    public final static int DIST_FACTOR = 300_000; // pre-calculated distance scaling factor
    public static int SIZE_FACTOR = 200; // pre-calculated scale scaling factor

    /** Resets the system(deletes all current planets and probes)
     */
    public static void resetSystem() {
        bodies.clear();
        probe = null;
    }

}
