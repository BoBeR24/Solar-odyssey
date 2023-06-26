package com.mygdx.game.GameLogic;

import java.util.LinkedList;

import com.mygdx.game.Objects.*;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.SupportiveClasses.Timer;

/**
 * controls stage of the algorithm which moves our probe to Titan
 */
public class HillClimbing {
    public static final int SECONDS_IN_YEAR = 31536000;
    public static int step = 1;
    public static Probe probe;
    public static Timer timer;
    public static boolean isOnTitanOrbit;
    public static boolean hasCompletedIteration;

    public static void hillClimb() {
        // gives the probe a thrust
        if (step == 1) {
            Pathfinding.toBody(probe, SolarSystem.bodies.get(SystemProperties.TITAN), 10000);
            if (SolarSystem.bodies.get(SystemProperties.TITAN).getLocation().subtract(probe.getLocation()).magnitude() < 5000) {
                step++;
                isOnTitanOrbit = true;
            }
        } else if (step == 2) {
            Pathfinding.inOrbit(probe, SolarSystem.bodies.get(SystemProperties.TITAN));
            if (timer.getTimePassed() > SECONDS_IN_YEAR / 2.0f) {
                step++;
            }
        }
    }
}
