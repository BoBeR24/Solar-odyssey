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
        for (Body planet : SolarSystem.planets){
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

    public static Vector Ralston (Body body, int stepSize){
        Body copy1 = body.clone();
        Vector k1 = calculateForce(copy1).multiply(stepSize);
        //TO-DO my problem is that i am updating copy but at the same time my steps depends on on the initial positions of the body so i dont
        // know how to connect it if i calculate it for the body at certain position
        //updateVelocityRalston(copy1, k1.multiply(0.75));
        updateCoordinateRalston(copy1, stepSize*3/4);
        // update position and velocity to calculate next step (here h/2)
        Vector k2 = calculateForce(copy1).multiply(stepSize);
        Vector finalForce = k1.multiply(1/3).add(k2.multiply(2/3));

        // k1 h * force calculated based on initial body positions
        // then I update the the body based on k1/2 so step size h/2 so we call it b1
        // then k2 h * force calculated based on b1
        // then I update the body based on k2/2 so h/2 so we call it b2
        // then k3 h * force calculated based on b2
        // then I update the body based on k3 so h so we call it b3
        // then k4 h * force caculated based on b3 
        // then I can finally update the proper body based on 1/6(k1 + 2*k2 + 2*k3 + k4)

        return finalForce;
    }

    public static Vector calculateForce(Body body){
        Vector forcesSum = new Vector(0.0, 0.0, 0.0); // sum of all forces

        //Loops through all celestial bodies except itself and the probe since an object cant affect itself
        for (Body planet : SolarSystem.planets){
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
        return forcesSum;
    }

    private static void updateVelocityRalston(Body body, Vector forcesSum){
        body.setVelocity(body.getVelocity().x + (forcesSum.x) / body.getMass(), body.getVelocity().y +
                (forcesSum.y ) / body.getMass(), body.getVelocity().z + (forcesSum.z ) / body.getMass());
    }

    private static void updateCoordinateRalston(Body body, int stepSize){
       body.setLocation((body.getLocation().x + body.getVelocity().x * stepSize), (body.getLocation().y +
               body.getVelocity().y * stepSize), (body.getLocation().z + body.getVelocity().z * stepSize));
    }
}
