package com.mygdx.game.GameLogic;

import com.mygdx.game.GUI.SolarSystemScreen;
import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.celestialBody;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.SupportiveClasses.Timer;

public class HillClimbing {
    public static boolean hasCompletedIteration = false;
    public static boolean isOnTitanOrbit = false;
    public static int step = 1;
    public static Timer timer;
    public static Probe probe;

    public static void hillClimb() {
        // gives the probe a thrust
        if (hasCompletedIteration && step == 1) {
            Pathfinding.toBody(probe, (celestialBody) SolarSystem.bodies.get(SystemProperties.TITAN), 400000);

            if (Math.abs(probe.getLocation().subtract(SolarSystem.bodies.get(SystemProperties.TITAN).getLocation()).magnitude()) < 3000000) {
                step++;
            }

        } else if (step == 2) {
            Pathfinding.inOrbit(probe, (celestialBody) SolarSystem.bodies.get(SystemProperties.TITAN));

            isOnTitanOrbit = true;

            if (timer.getTimePassed() >= 525600) {     //31536000
                step++;
            }
        } else if (step == 3) {
            Pathfinding.toBody(probe, (celestialBody) SolarSystem.bodies.get(SystemProperties.EARTH), 0);

            if (probe.getLocation().subtract(SolarSystem.bodies.get(SystemProperties.EARTH).getLocation()).magnitude() < 6371) {
                SolarSystemScreen.state = State.PAUSED;
                System.out.println(Rocketry.fuel);
            }
        }
    }
}
