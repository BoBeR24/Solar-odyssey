package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;

public class RK4_new {
    private static int STEPSIZE = PhysicsUtils.STEPSIZE;
    private static Vector[][] k_positions;
    private static Vector[][] k_velocities;
//    private static ArrayList<Body> clonedBodies = new ArrayList<>();
//    private static ArrayList<Body> bodies = new ArrayList<>();

    public static void calculate() {
        //TODO make code compatible of working with more than one probe(may require changing id in Probe class)
//        celestialBody[] initPlanets = new celestialBody[SolarSystem.planets.size()];
//        ArrayList<Probe> initProbes = new ArrayList<>(); // use arraylist for probes because their initial amount may vary

//        clonedBodies.clear();
//        bodies.clear();

//        // save initial state of all planets and probes
//        for (celestialBody planet : SolarSystem.planets) {
////            initPlanets[planet.getId()] = planet.clone();
//            clonedBodies.add(planet.clone());
//        }
//
//        for (Probe probe : SolarSystem.probes) {
//            clonedBodies.add(probe.clone());
//        }
//
//        bodies.addAll(SolarSystem.planets);
//        bodies.addAll(SolarSystem.probes);

        k_positions = new Vector[4][SolarSystem.planets.size() + SolarSystem.probes.size()];
        k_velocities = new Vector[4][SolarSystem.planets.size() + SolarSystem.probes.size()];
//
//        k_positions = new Vector[4][clonedBodies.size()];
//        k_velocities = new Vector[4][clonedBodies.size()];


        // calculate k1 for planets
        for (celestialBody planet : SolarSystem.planets) {
            k1State(planet.clone());
        }

        // for probes
        for (Probe probe : SolarSystem.probes) {
            k1State(probe.clone());
        }

        // calculate k2 for planets
        for (celestialBody planet: SolarSystem.planets) {
            k2State(planet.clone());
        }

//        // for probes
        for (Probe probe : SolarSystem.probes) {
            k2State(probe.clone());
        }

        // calculate k3 for planets
        for (celestialBody planet: SolarSystem.planets) {
            k3State(planet.clone());
        }

//        // for probes
        for (Probe probe : SolarSystem.probes) {
            k3State(probe.clone());
        }

        // calculate k4 for planets
        for (celestialBody planet : SolarSystem.planets) {
            k4State(planet.clone());
        }

//        // for probes
        for (Probe probe : SolarSystem.probes) {
            k4State(probe.clone());
        }

//        System.out.println(k_positions[3][SystemProperties.EARTH]);

        // calculate final new state for planets
        for (celestialBody planet: SolarSystem.planets) {
            final_state(planet);
        }

//        System.out.println(SolarSystem.probes.get(0).getLocation());
        // for probes
        for (Probe probe : SolarSystem.probes) {
            final_state(probe);
        }


    }

    /** Calculates k1 state of given body
     * */
    private static void k1State(Body body) {
        // calculate k1 for planets
        Vector force = PhysicsUtils.allForce(body);

        // save k1 for position and velocity
        k_positions[0][body.getId()] = calculateCoordinate(body, STEPSIZE);
        k_velocities[0][body.getId()] = calculateVelocity(body, force, STEPSIZE);
        //TODO problem may be in formula, which time step do we use to calculate next k
    }

    private static void k2State(Body body) {
        // update location and velocity of a cloned body to Yn + h * k1 / 2 position to calculate force in that position
        body.setNextLocation(body.getLocation().add(
                k_positions[0][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.setNextVelocity(body.getVelocity().add(
                k_velocities[0][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.update();

        Vector force = PhysicsUtils.allForce(body);

        k_positions[1][body.getId()] = calculateCoordinate(body, STEPSIZE / 2.0);
        k_velocities[1][body.getId()] = calculateVelocity(body, force, STEPSIZE / 2.0);
    }

    private static void k3State(Body body) {
        // update location and velocity of a cloned body to Yn + h * k2 / 2 position to calculate force in that position
        body.setNextLocation(body.getLocation().add(
                k_positions[1][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.setNextVelocity(body.getVelocity().add(
                k_velocities[1][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.update();

        Vector force = PhysicsUtils.allForce(body);

        k_positions[2][body.getId()] = calculateCoordinate(body, STEPSIZE / 2.0);
        k_velocities[2][body.getId()] = calculateVelocity(body, force, STEPSIZE / 2.0);
    }

    private static void k4State(Body body) {
        // update location to k3 state
        body.setNextLocation(body.getLocation().add(
                k_positions[2][body.getId()].multiply(STEPSIZE))
        );

        // update velocity to k3 state
        body.setNextVelocity(body.getVelocity().add(
                k_velocities[2][body.getId()].multiply(STEPSIZE))
        );

        body.update();

        Vector force = PhysicsUtils.allForce(body);

        k_positions[3][body.getId()] = calculateCoordinate(body, STEPSIZE);
        k_velocities[3][body.getId()] = calculateVelocity(body, force, STEPSIZE);
    }

    private static void final_state(Body body) {
        Vector next_location = k_positions[0][body.getId()]; // k1
        next_location = next_location.add(k_positions[1][body.getId()].multiply(2.0)); // k1 + 2 * k2
        next_location = next_location.add(k_positions[2][body.getId()].multiply(2.0)); // k1 + 2 * k2 + 2 * k3
        next_location = next_location.add(k_positions[3][body.getId()]); // k1 + 2 * k2 + 2 * k3 + k4
        next_location = next_location.multiply(1.0 / 6.0); // h/6 * (k1 + 2 * k2 + 2 * k3 + k4)
        next_location = next_location.add(body.getLocation()); // Yn + h/6 * (k1 + 2 * k2 + 2 * k3 + k4)

        body.setNextLocation(next_location);


        Vector next_velocity = k_velocities[0][body.getId()]; // k1
        next_velocity = next_velocity.add(k_velocities[1][body.getId()].multiply(2.0)); // k1 + 2 * k2
        next_velocity = next_velocity.add(k_velocities[2][body.getId()].multiply(2.0)); // k1 + 2 * k2 + 2 * k3
        next_velocity = next_velocity.add(k_velocities[3][body.getId()]); // k1 + 2 * k2 + 2 * k3 + k4
        next_velocity = next_velocity.multiply(1.0 / 6.0); // h/6 * (k1 + 2 * k2 + 2 * k3 + k4)
        next_velocity = next_velocity.add(body.getVelocity()); // Yn + h/6 * (k1 + 2 * k2 + 2 * k3 + k4)

        body.setNextLocation(next_location);
        body.setNextVelocity(next_velocity);

        // BELOW IS INCORRECT FORMULA(BUT WORKS BETTER)

//        Vector next_location = k_positions[0][body.getId()]; // k1
//        next_location = next_location.add(k_positions[1][body.getId()]); // k1 + k2
//        next_location = next_location.add(k_positions[2][body.getId()]); // k1 + k2 + k3
//        next_location = next_location.add(k_positions[3][body.getId()]); // k1 + k2 + k3 + k4
//        next_location = next_location.multiply(STEPSIZE / 6.0); // h / 6 * (k1 + k2 + k3 + k4)
//        next_location = next_location.add(body.getLocation()); //Yn + h / 6 * (k1 + k2 + k3 + k4)
//
//        Vector next_velocity = k_velocities[0][body.getId()]; // k1
//        next_velocity = next_velocity.add(k_velocities[1][body.getId()]); // k1 + k2
//        next_velocity = next_velocity.add(k_velocities[2][body.getId()]); // k1 + k2 + k3
//        next_velocity = next_velocity.add(k_velocities[3][body.getId()]); // k1 + k2 + k3 + k4
//        next_velocity = next_velocity.multiply(STEPSIZE / 6.0); // h / 6 * (k1 + k2 + k3 + k4)
//        next_velocity = next_velocity.add(body.getVelocity()); //Yn + h / 6 * (k1 + k2 + k3 + k4)
//
//        body.setNextLocation(next_location);
//        body.setNextVelocity(next_velocity);

    }

    /** calculate new coordinates of the body. In fact if we look at the formula of RK4 method this can be considered as
     * f(y) for coordinates
     * */
    private static Vector calculateCoordinate(Body body, double step){
        return new Vector(
                (body.getNextVelocity().x * step),
                (body.getNextVelocity().y * step),
                (body.getNextVelocity().z * step));
    }

    /** calculate new velocity of the body. In fact if we look at the formula of RK4 method this can be considered as
     * f() for velocities
     * */
    private static Vector calculateVelocity(Body body, Vector forcesSum, double step){
        return new Vector(
                (forcesSum.x * step) / body.getMass(),
                (forcesSum.y * step) / body.getMass(),
                (forcesSum.z * step) / body.getMass());
    }
}
