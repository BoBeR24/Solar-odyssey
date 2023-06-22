package com.mygdx.game.GameLogic;

import java.util.LinkedList;

import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.SupportiveClasses.Resetter;
import com.mygdx.game.SupportiveClasses.Timer;

public class HillClimbing {
    public static int step = 1;
    public static Probe probe;
    public static Resetter resetter;
    public static Timer timer;
    
    public static final int SECONDS_IN_WEEK = 604800;
    public static final int SECONDS_IN_YEAR = 31536000;

    private LinkedList bestPath;

    public static void hillClimb() {
        // gives the probe a thrust
        if (step == 1) { 
            if (timer.getTimePassed() % SECONDS_IN_WEEK == 0);
            Positions.addPosition("Titan" + timer.getTimePassed() / SECONDS_IN_WEEK, SolarSystem.bodies.get(SystemProperties.TITAN).getLocation());
            if (timer.getTimePassed() >= SECONDS_IN_YEAR){
                Resetter.resetSimulation();
                step++;
            }

        } else if (step == 2) {
            //TODO launch probes at all different positions in every week recorded and find the most fuel efficient. Make sure to save thrust mapping
            // Mapping will be done with a linked list

        } else if (step == 3) {
            //TODO launch probe with thrust mapping found in step 2

        } else if (step == 4) {
            //TODO get in orbit
        } else if (step == 5) {
            //TODO get to surface
        }
    }
}
