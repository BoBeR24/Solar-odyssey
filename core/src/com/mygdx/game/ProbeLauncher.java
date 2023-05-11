package com.mygdx.game;

public class ProbeLauncher {
    /**
     * @param velocity to add to earth velocity
     * */
    public static void launch(Vector velocity){
        Probe probe = new Probe(velocity);
        SolarSystem.probes.add(probe);
    }
}
