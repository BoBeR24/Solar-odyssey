package com.mygdx.game.GameLogic;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.LandingModule;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Objects.Vector;

public class ProbeLauncher {
    /** launches probe and adds it to the system
     * @param velocity to add to earth velocity
     * */
    public static void launchProbe(Vector velocity){
        Probe probe = new Probe(velocity);
        SolarSystem.probe = probe;
        SolarSystem.bodies.add(probe);
    }

    public static void launchLandingModule(Vector location, Vector velocity, int mass) {
        LandingModule rocket = new LandingModule(location, velocity, mass);
        SolarSystem.landingModule = rocket;
        SolarSystem.bodies.add(rocket);
    }
}
