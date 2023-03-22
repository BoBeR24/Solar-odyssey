package com.mygdx.game;

// Calculates forces between a body and Spacecraft

public class PhysicsUtils{

    //Still have to change
    public final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 3600;
    private final static Vector[] velocities = SystemProperties.velocities;
    private final static Vector[] coordinates = SystemProperties.coordinates;
    private final static double[] masses = SystemProperties.masses;

    public static void updateBody(celestialBody body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        for (celestialBody planet : SolarSystem.bodies){
            if (planet == body || body.getName().equals("Probe")) { // skips iteration where body and body are the same object, because object can't affect itself
                continue;
            }

                double scalingFactor = gravitationalConstant * body.getMass() * body.getMass() * (-1);

                Vector bodyvector = body.getLocation();
                Vector Spacecraftvector = body.getLocation();

                Vector force = Spacecraftvector.subtract(bodyvector);

                double magnitude = Math.pow(force.magnitude(), 3);

                force.multiply(scalingFactor / magnitude);

                forcesSum.add(force);
        }


        updateVelocity(body, forcesSum);
        updateCoordinate(body);
        
    }
    
     private static void updateVelocity(celestialBody body, Vector forcesSum){
        int index = body.getId();
        velocities[index].set(velocities[index].x + ((forcesSum.x) * STEPSIZE) / masses[index], velocities[index].y +
                ((forcesSum.y) * STEPSIZE) / masses[index], velocities[index].z + ((forcesSum.z) * STEPSIZE) / masses[index]);
    }

    private static void updateCoordinate(celestialBody body){
        int index = body.getId();
        coordinates[index].set((coordinates[index].x + velocities[index].x* STEPSIZE) , (coordinates[index].y +
                velocities[index].y* STEPSIZE) , (coordinates[index].z + velocities[index].z* STEPSIZE) );

        
    }

}
