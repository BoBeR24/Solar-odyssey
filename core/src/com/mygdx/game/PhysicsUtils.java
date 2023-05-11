package com.mygdx.game;

/**
 * class with methods for calculating and updating physical processes of bodies presented in the system
 * */
public class PhysicsUtils{
    //Gravitational constant expressed in cubic kilometers per kilogram per second squared
    private final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
    public final static int STEPSIZE = 30;

   /**
    * Calculates sum of the forces and initializes methods for updating velocity and coordinates
    * @param body object that the forces are being exerted on
    */
    public static void calculateNextState(Body body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        // Loops through all celestial bodies except itself and the probe since an object cant affect itself
        for (Body planet : SolarSystem.planets){
            if (planet.getId() == body.getId() || planet.getId() == SystemProperties.PROBE) {
                continue;
            }

            double scalingFactor = gravitationalConstant * planet.getMass() * body.getMass() * (-1);

            Vector planetVector = planet.getLocation();
            Vector bodyVector = body.getLocation();

            //Difference between the vectors of the two celestial bodies
            Vector force = bodyVector.subtract(planetVector);

            double magnitude = Math.pow(force.magnitude(), 3);

            force = force.multiply(scalingFactor / magnitude);

            //adds forces by all bodies exerted on body
            forcesSum = forcesSum.add(force);
        }

        updateVelocity(body, forcesSum);
        updateCoordinate(body);
        
    }
    
    private static void updateVelocity(Body body, Vector forcesSum){
        int index = body.getId();

        // if body is a probe update its properties immediately, if it is a planet write them to nextState array
        if (index != SystemProperties.PROBE) {

            SystemProperties.velocities_nextState[index].set(
                    body.getVelocity().x + (forcesSum.x * STEPSIZE) / body.getMass(),
                    body.getVelocity().y + (forcesSum.y * STEPSIZE) / body.getMass(),
                    body.getVelocity().z + (forcesSum.z * STEPSIZE) / body.getMass()
            );

            return;
        }

        body.setVelocity(
                body.getVelocity().x + (forcesSum.x * STEPSIZE) / body.getMass(),
                body.getVelocity().y + (forcesSum.y * STEPSIZE) / body.getMass(),
                body.getVelocity().z + (forcesSum.z * STEPSIZE) / body.getMass()
        );
    }

    private static void updateCoordinate(Body body){
        int index = body.getId();

        // if body is a probe update its properties immediately, if it is a planet write them to nextState array
        if (index != SystemProperties.PROBE) {

            SystemProperties.coordinates_nextState[index].set(
                    (body.getLocation().x + body.getVelocity().x * STEPSIZE),
                    (body.getLocation().y + body.getVelocity().y * STEPSIZE),
                    (body.getLocation().z + body.getVelocity().z * STEPSIZE)
            );

            return;
        }

        body.setLocation(
                (body.getLocation().x + body.getVelocity().x * STEPSIZE),
                (body.getLocation().y + body.getVelocity().y * STEPSIZE),
                (body.getLocation().z + body.getVelocity().z * STEPSIZE)
        );
    }

}
