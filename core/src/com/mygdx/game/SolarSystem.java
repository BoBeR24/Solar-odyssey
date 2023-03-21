package com.mygdx.game;


import com.mygdx.game.celestialBody;


public class SolarSystem {

    celestialBody [] planets;

    SolarSystem(celestialBody sun, celestialBody mercury, celestialBody venus, celestialBody earth, celestialBody moon, celestialBody mars, celestialBody jupiter, celestialBody saturn, celestialBody titan, celestialBody probe){
        planets = new celestialBody[10];
        planets[0] = sun;
        planets[1] = mercury;
        planets[2] = venus;
        planets[3] = earth;
        planets[4] = moon;
        planets[5] = mars;
        planets[6] = jupiter;
        planets[7] = saturn;
        planets[8] = titan;
        planets[9] = probe;
    }

    public celestialBody[] getSolarSystem(){
        return planets;
    }
}
