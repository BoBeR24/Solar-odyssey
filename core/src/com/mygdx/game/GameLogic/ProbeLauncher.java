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
        SolarSystem.bodies.add(probe);
    }
}
