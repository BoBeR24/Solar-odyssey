package com.mygdx.game;

/**
 * class with methods for calculating and updating physical processes of bodies presented in the system
 * */
public class PhysicsUtils{
    //Gravitational constant expressed in cubic kilometers per kilogram per second squared
    private final static double gravitationalConstant = 6.6743 * Math.pow(10, -20);
//    private final static int STEPSIZE = 86400;
    public final static int STEPSIZE = 30;
    private final static Vector[] velocities = SystemProperties.velocities;
    private final static Vector[] coordinates = SystemProperties.coordinates;
    private final static double[] masses = SystemProperties.masses;

    public final static Vector[] coordinates_nextState = coordinates.clone();
    public final static Vector[] velocities_nextState = velocities.clone();

   /**
    * Calculates sum of the forces and initializes methods for updating velocity and coordinates
    * @param body object that the forces are being exerted on
    */
    public static void updateBody(Body body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        //Loops through all celestial bodies except itself and the probe since an object cant affect itself
        for (celestialBody planet : SolarSystem.planets){
            if (planet.getName().equals(body.getName()) || planet.getClass().getSimpleName().equals("Probe")) {
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
        // if body is a probe update its properties immediately, if it is a planet write them to nextState array
        if (!body.getClass().getSimpleName().equals("Probe")) {
            int index = ((celestialBody) body).getId(); // hate myself for having to do that

            velocities_nextState[index].set(velocities[index].x + (forcesSum.x * STEPSIZE) / masses[index], velocities[index].y +
                    (forcesSum.y * STEPSIZE) / masses[index], velocities[index].z + (forcesSum.z * STEPSIZE) / masses[index]);

            return;
        }

        body.setVelocity(body.getVelocity().x + (forcesSum.x * STEPSIZE) / body.getMass(), body.getVelocity().y +
                (forcesSum.y * STEPSIZE) / body.getMass(), body.getVelocity().z + (forcesSum.z * STEPSIZE) / body.getMass());
    }

    private static void updateCoordinate(Body body){
       if (!body.getClass().getSimpleName().equals("Probe")) {
           int index = ((celestialBody) body).getId();

           coordinates_nextState[index].set((coordinates[index].x + velocities[index].x * STEPSIZE), (coordinates[index].y +
                   velocities[index].y * STEPSIZE), (coordinates[index].z + velocities[index].z * STEPSIZE));

           return;
       }

       body.setLocation((body.getLocation().x + body.getVelocity().x * STEPSIZE), (body.getLocation().y +
               body.getVelocity().y * STEPSIZE), (body.getLocation().z + body.getVelocity().z * STEPSIZE));
    }

    public static Vector distanceToTitan(Probe probe, celestialBody target) {
        Vector probe_location = probe.getLocation();
        Vector target_location = target.getLocation();

//        return probe_location.subtract(target_location).magnitude();
        return probe_location.subtract(target_location);
    }

}
