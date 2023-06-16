package com.mygdx.game.SupportiveClasses;

import com.mygdx.game.GameLogic.*;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.*;

public class Resetter {

    

    public static void resetSimulation(){
        for (int i = 0; i < SolarSystem.bodies.size()-1; i++){
            SolarSystem.bodies.get(i).setLocation(SystemProperties.initCoordinates[i]);
            SolarSystem.bodies.get(i).setNextLocation((SystemProperties.initCoordinates[i]));            
            SolarSystem.bodies.get(i).setVelocity(SystemProperties.initVelocities[i]);
            SolarSystem.bodies.get(i).setNextVelocity(SystemProperties.initVelocities[i]);
        }
        ProbeLauncher.launch(new Vector(41.2384, -15.006862175, -3.183));
        HillClimbing.timer.reset();
        }
}
