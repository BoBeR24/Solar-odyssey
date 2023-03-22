package com.mygdx.game;

// Calculates forces between a planet and Spacecraft

public class PhysicsUtils{

    //Still have to change
    public final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 3600;

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
        velocities[index].set(velocities[index].x+ ((finalForce.x)*STEPSIZE)/masses[index], velocities[index].y+ ((finalForce.y)*STEPSIZE)/masses[index], velocities[index].z+ ((finalForce.z)*STEPSIZE)/masses[index]);
    }

    public void newCoordinate(){
        int index = entities.get(planet);
        coordinates[index].set((coordinates[index].x+velocities[index].x)*STEPSIZE, (coordinates[index].y+velocities[index].y)*STEPSIZE, (coordinates[index].z+velocities[index].z)*STEPSIZE);

        
    }

}
