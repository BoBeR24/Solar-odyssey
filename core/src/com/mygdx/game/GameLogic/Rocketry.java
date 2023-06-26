package com.mygdx.game.GameLogic;

import com.mygdx.game.Objects.Probe;
import com.mygdx.game.Objects.Vector;

/**
 * class that gives a probe object rocket functionality
 */
public class Rocketry {
    public static double fuel; // Fuel in Delta-V

    /**
     * Method to make a probe go brrr(thrust it means), vector in km/s
     *
     * @param probe  probe to thrust
     * @param thrust force we want to apply as a thrust
     */
    public static void thrust(Probe probe, Vector thrust) {
        probe.setNextVelocity(probe.getNextVelocity().add(thrust));
        fuel = fuel + thrust.magnitude() / 1000;
    }
}
