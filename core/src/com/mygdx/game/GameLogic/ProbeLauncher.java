package com.mygdx.game.GameLogic;

import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Objects.Vector;

public class ProbeLauncher {
    /**
     * @param velocity to add to earth velocity
     * */
    public static void launch(Vector velocity){
        Probe probe = new Probe(velocity);
        SolarSystem.probe = probe;
        if (SolarSystem.bodies.size() >= 12){
            SolarSystem.bodies.set(11, probe);
        } else {
            SolarSystem.bodies.add(probe);
        }
    }
}
