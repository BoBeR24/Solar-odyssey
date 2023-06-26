package com.mygdx.game.GameLogic;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.LandingModule;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Objects.Vector;

/**
 * Manages probe and landing module launching
 * */
public class ProbeLauncher {
    /** launches probe and adds it to the system
     * @param velocity to add to earth velocity
     * */
    public static void launchProbe(Vector velocity){
        Probe probe = new Probe(velocity);
        SolarSystem.probe = probe;
        SolarSystem.bodies.add(probe);
    }

    public static void launchLandingModule(Vector location, Vector velocity, double rotation) {
        LandingModule landingModule = new LandingModule(location, velocity, rotation);
        SolarSystem.landingModule = landingModule;
        SolarSystem.bodies.add(landingModule);
    }
}
