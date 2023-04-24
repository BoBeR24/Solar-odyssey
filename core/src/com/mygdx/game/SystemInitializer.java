package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/** Class which is responsible for setting up initial solar system(e.g. adding planets to it)
 * */
public class SystemInitializer {
    /** adds all planets to the system and setting up their names and colors
     * */
    public static void fillSystemWithPlanets() {
        ArrayList<celestialBody> planets = SolarSystem.planets;

        celestialBody sun = new celestialBody("Sun"); // add all bodies here
        sun.setColor(Color.YELLOW);
        planets.add(sun);

        celestialBody mercury = new celestialBody("Mercury");
        mercury.setColor(Color.valueOf("#C0C2C9"));
        planets.add(mercury);

        celestialBody venus = new celestialBody("Venus");
        venus.setColor(Color.valueOf("#f7e4ab"));
        planets.add(venus);

        celestialBody earth = new celestialBody("Earth");
        earth.setColor(Color.BLUE);
        planets.add(earth);

        celestialBody moon = new celestialBody("Moon");
        moon.setColor(Color.WHITE);
        planets.add(moon);

        celestialBody mars = new celestialBody("Mars");
        mars. setColor(Color.valueOf("#D6723B"));
        planets.add(mars);

        celestialBody jupiter = new celestialBody("Jupiter");
        jupiter.setColor(Color.valueOf("#c99039"));
        planets.add(jupiter);

        celestialBody saturn = new celestialBody("Saturn");
        saturn.setColor(Color.valueOf("#cae8b0c"));
        planets.add(saturn);

        celestialBody titan = new celestialBody("Titan");
        titan.setColor(Color.valueOf("#f2a900"));
        planets.add(titan);

        celestialBody neptune = new celestialBody("Neptune");
        neptune.setColor(Color.valueOf("#d1e7e7"));
        planets.add(neptune);

        celestialBody uranus = new celestialBody("Uranus");
        uranus.setColor(Color.valueOf("#5b5ddf"));
        planets.add(uranus);
    }
}
