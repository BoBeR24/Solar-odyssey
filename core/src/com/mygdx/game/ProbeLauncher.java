package com.mygdx.game;

public class ProbeLauncher {
    /**
     * @param velocity to add to earth velocity
     * */
    public static void launch(Vector velocity){
        Probe probe = new Probe(velocity);
        probe.setVelocity(probe.getVelocity().add(velocity)); // add initial velocity
        SolarSystem.probes.add(probe);
    }
}
