package com.mygdx.game.GameLogic;

import com.mygdx.game.GUI.SolarSystemScreen;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.celestialBody;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.SupportiveClasses.Resetter;
import com.mygdx.game.SupportiveClasses.Timer;

public class HillClimbing {
    public static boolean hasCompletedIteration = false;
    public static int step = 1;
    public static Probe probe;
    public static Resetter resetter;
    public static Timer timer;

    public static void hillClimb() {
        // gives the probe a thrust
        if (hasCompletedIteration && step == 1) {
            //Pathfinding.toBody(probe, (celestialBody) SolarSystem.bodies.get(SystemProperties.TITAN), 400000);
            
            if (timer.getTimePassed() > 1000000) {
                step++;
            }
            //System.out.println(step + " " + timer.getTimePassed());

        } else if (step == 2) {
            resetter.resetSimulation();
            System.out.println("reset");
            step = 1;
        }
    }
}
