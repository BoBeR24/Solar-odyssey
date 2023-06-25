package com.mygdx.game.GameLogic;

import java.util.LinkedList;

import com.mygdx.game.Objects.*;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.SupportiveClasses.Timer;

public class HillClimbing {
    public static int step = 1;
    public static Probe probe;
    public static Timer timer;
    public static boolean isOnTitanOrbit;
    public static boolean hasCompletedIteration;
    
    public static final int SECONDS_IN_WEEK = 604800;
    public static final int SECONDS_IN_YEAR = 31536000;

    public static void hillClimb() {
        // gives the probe a thrust
        if (step == 1) {
            Pathfinding.toBody(probe, (celestialBody) SolarSystem.bodies.get(SystemProperties.TITAN), 10000);
            if (probe.getDistanceToTitan() < 900000){
                step++;
                isOnTitanOrbit = true;
            }
        } else if (step == 2) {
            Pathfinding.inOrbit(probe, (celestialBody) SolarSystem.bodies.get(SystemProperties.TITAN));
            if (timer.getTimePassed() > SECONDS_IN_YEAR / 2.0f){
                step++;
            }
        }
    }
}
