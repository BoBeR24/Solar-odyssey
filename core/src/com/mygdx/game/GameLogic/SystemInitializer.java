package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Objects.Body;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;
import com.mygdx.game.Objects.celestialBody;

import java.util.ArrayList;

/**
 * Class which is responsible for setting up initial solar system(e.g. adding planets to it)
 */
public class SystemInitializer {
    /**
     * adds all planets to the system and setting up their names and colors
     */
    public static void fillSystemWithPlanets() {
        ArrayList<Body> planets = SolarSystem.bodies;

        celestialBody sun = new celestialBody(SystemProperties.SUN); // add all bodies here
        sun.setColor(Color.YELLOW);
        planets.add(sun);

        celestialBody mercury = new celestialBody(SystemProperties.MERCURY);
        mercury.setColor(Color.valueOf("#C0C2C9"));
        planets.add(mercury);

        celestialBody venus = new celestialBody(SystemProperties.VENUS);
        venus.setColor(Color.valueOf("#f7e4ab"));
        planets.add(venus);

        celestialBody earth = new celestialBody(SystemProperties.EARTH);
        earth.setColor(Color.BLUE);
        planets.add(earth);

        celestialBody moon = new celestialBody(SystemProperties.MOON);
        moon.setColor(Color.WHITE);
        planets.add(moon);

        celestialBody mars = new celestialBody(SystemProperties.MARS);
        mars.setColor(Color.valueOf("#D6723B"));
        planets.add(mars);

        celestialBody jupiter = new celestialBody(SystemProperties.JUPITER);
        jupiter.setColor(Color.valueOf("#c99039"));
        planets.add(jupiter);

        celestialBody saturn = new celestialBody(SystemProperties.SATURN);
        saturn.setColor(Color.valueOf("#cae8b0c"));
        planets.add(saturn);

        celestialBody titan = new celestialBody(SystemProperties.TITAN);
        titan.setColor(Color.valueOf("#f2a900"));
        planets.add(titan);

        celestialBody neptune = new celestialBody(SystemProperties.NEPTUNE);
        neptune.setColor(Color.valueOf("#d1e7e7"));
        planets.add(neptune);

        celestialBody uranus = new celestialBody(SystemProperties.URANUS);
        uranus.setColor(Color.valueOf("#5b5ddf"));
        planets.add(uranus);
    }
}
