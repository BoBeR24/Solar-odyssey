package com.mygdx.game;

// Calculates forces between a planet and Spacecraft

public class PhysicsUtils{

    //Still have to change
    public final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);

    public static Vector actingForces(celestialBody body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        for (celestialBody planet : SolarSystem.bodies){
            if (planet == body) { // skips iteration where planet and body are the same object, because object can't affect itself
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
    
     public void newVelocity(){
        int index = entities.get(planet); 
        velocities[index].set(velocities[index].x+ ((finalForce.x)*3600)/masses[index], velocities[index].y+ ((finalForce.y)*3600)/masses[index], velocities[index].z+ ((finalForce.z)*3600)/masses[index]);

    }

    public void newCoordinate(){
        int index = entities.get(planet);
        coordinates[index].set((coordinates[index].x+velocities[index].x)*3600, (coordinates[index].y+velocities[index].y)*3600, (coordinates[index].z+velocities[index].z)*3600);

        
    }

}
