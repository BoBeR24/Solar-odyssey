package com.mygdx.game;

/**
 * class with methods for calculating and updating physical processes of bodies presented in the system
 * */
public class PhysicsUtils{
    //Still have to change
    private final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    private final static int STEPSIZE = 14440;
    private final static Vector[] velocities = SystemProperties.velocities;
    private final static Vector[] coordinates = SystemProperties.coordinates;
    private final static double[] masses = SystemProperties.masses;

    public final static Vector[] coordinates_nextState = coordinates.clone();
    public final static Vector[] velocities_nextState = velocities.clone();

    /**
     * Calculates sum of the forces and initializes methods for updating velocity and coordinates
     * */
    public static void updateBody(celestialBody body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        for (celestialBody planet : SolarSystem.bodies){
            if (planet.getId() == body.getId() || planet.getName().equals("Probe")) { // skips iteration where body and body are the same object, because object can't affect itself
                continue;
            }

            double scalingFactor = gravitationalConstant * planet.getMass() * body.getMass() * (-1);

            Vector planetVector = planet.getLocation();
            Vector bodyVector = body.getLocation();

            Vector force = bodyVector.subtract(planetVector);

            double magnitude = Math.pow(force.magnitude(), 3);

            force = force.multiply(scalingFactor / magnitude);

            forcesSum = forcesSum.add(force);
        }

        updateVelocity(body, forcesSum);
        updateCoordinate(body);
        
    }
    
     private static void updateVelocity(celestialBody body, Vector forcesSum){
        int index = body.getId();

         velocities_nextState[index].set(velocities[index].x + (forcesSum.x * STEPSIZE) / masses[index], velocities[index].y +
                 (forcesSum.y * STEPSIZE) / masses[index], velocities[index].z + (forcesSum.z * STEPSIZE) / masses[index]);
    }

    private static void updateCoordinate(celestialBody body){
        int index = body.getId();

        coordinates_nextState[index].set((coordinates[index].x + velocities[index].x * STEPSIZE), (coordinates[index].y +
                velocities[index].y * STEPSIZE), (coordinates[index].z + velocities[index].z * STEPSIZE));

        
    }

}
