package com.mygdx.game;

import java.util.ArrayList;

public class ProbeLauncher {
    /**
     * @param velocity to add to earth velocity
     * @param location on earths surface
     * */
    public static void launch(Vector velocity){
        Probe probe = new Probe();
        probe.setVelocity(probe.getVelocity().add(velocity)); // add initial velocity
        SolarSystem.probes.add(probe);
    }
}
