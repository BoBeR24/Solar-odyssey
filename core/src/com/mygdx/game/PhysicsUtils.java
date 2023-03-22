package com.mygdx.game;

// Calculates forces between a planet and Spacecraft

import java.util.Hashtable;
import java.util.Objects;

public class PhysicsUtils{

    //Still have to change
    public final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 3600;
    private final static Hashtable<String, Integer> entities = SystemProperties.entities;
    private final static Vector[] velocities = SystemProperties.velocities;
    private final static Vector[] coordinates = SystemProperties.coordinates;
    private final static double[] masses = SystemProperties.masses;

    public static Vector actingForces(celestialBody body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        for (celestialBody planet : SolarSystem.bodies){
            if (planet == body || planet.getName().equals("Probe")) { // skips iteration where planet and body are the same object, because object can't affect itself
                continue;
            }

                double scalingFactor = gravitationalConstant * planet.getMass() * body.getMass() * (-1);

                Vector planetvector = planet.getLocation();
                Vector Spacecraftvector = body.getLocation();

                Vector force = Spacecraftvector.subtract(planetvector);

                double magnitude = Math.pow(force.magnitude(), 3);

                force.multiply(scalingFactor / magnitude);

                forcesSum.add(force);
        }


        return forcesSum;
        
    }
    
     public static void newVelocity(celestialBody planet, Vector finalForce){
        int index = entities.get(planet.getName());
        velocities[index].set(velocities[index].x + ((finalForce.x) * STEPSIZE) / masses[index], velocities[index].y +
                ((finalForce.y) * STEPSIZE) / masses[index], velocities[index].z + ((finalForce.z) * STEPSIZE) / masses[index]);
    }

    public void newCoordinate(celestialBody planet){
        int index = entities.get(planet.getName());
        coordinates[index].set((coordinates[index].x + velocities[index].x) * STEPSIZE, (coordinates[index].y +
                velocities[index].y) * STEPSIZE, (coordinates[index].z + velocities[index].z) * STEPSIZE);

        
    }

}
