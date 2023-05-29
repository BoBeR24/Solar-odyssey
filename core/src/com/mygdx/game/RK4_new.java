package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;

public class RK4_new {
    private static int STEPSIZE = PhysicsUtils.STEPSIZE;
    private static Vector[][] k_positions;
    private static Vector[][] k_velocities;
    private static ArrayList<Body> bodies = new ArrayList<>();

    public static void calculate() {
        //TODO make code compatible of working with more than one probe(may require changing id in Probe class)
//        celestialBody[] initPlanets = new celestialBody[SolarSystem.planets.size()];
//        ArrayList<Probe> initProbes = new ArrayList<>(); // use arraylist for probes because their initial amount may vary

        bodies.clear();

        // save initial state of all planets and probes
        for (celestialBody planet : SolarSystem.planets) {
//            initPlanets[planet.getId()] = planet.clone();
            bodies.add(planet.clone());
        }

        for (Probe probe : SolarSystem.probes) {
            bodies.add(probe.clone());
        }

//        Vector[][] k_positions = new Vector[4][initPlanets.length + initProbes.size()];
//        Vector[][] k_velocities = new Vector[4][initPlanets.length + initProbes.size()];

        k_positions = new Vector[4][bodies.size()];
        k_velocities = new Vector[4][bodies.size()];


        // calculate k1 for planets
        for (Body body : bodies) {
            k1State(body);
        }

        // for probes
//        for (Probe probe : SolarSystem.probes) {
//            k1State(probe);
//        }

        // calculate k2 for planets
        for (Body body : bodies) {
            k2State(body);
        }

//        // for probes
//        for (Probe probe : SolarSystem.probes) {
//            k2State(probe);
//        }

        // calculate k3 for planets
        for (Body body : bodies) {
            k3State(body);
        }

//        // for probes
//        for (Probe probe : SolarSystem.probes) {
//            k3State(probe);
//        }

        // calculate k4 for planets
        for (Body body : bodies) {
            k4State(body);
        }

//        // for probes
//        for (Probe probe : SolarSystem.probes) {
//            k4State(probe);
//        }

//        System.out.println(k_positions[3][SystemProperties.EARTH]);

        // calculate final new state for planets
        for (Body body : bodies) {
            final_state(body);
        }

//        System.out.println(SolarSystem.probes.get(0).getLocation());
        // for probes
//        for (Probe probe : SolarSystem.probes) {
//            final_state(probe);
//        }
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
        // update location and velocity to Yn + h * k1 / 2 position
        body.setNextLocation(body.getLocation().add(
                k_positions[0][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.setNextVelocity(body.getVelocity().add(
                k_velocities[0][body.getId()].multiply(STEPSIZE / 2.0))
        );

//        body.update();

        Vector force = PhysicsUtils.allForce(body);

        k_positions[1][body.getId()] = calculateCoordinate(body, STEPSIZE / 2.0);
        k_velocities[1][body.getId()] = calculateVelocity(body, force, STEPSIZE / 2.0);
    }

    private static void k3State(Body body) {
        // update location and velocity to Yn + h * k2 / 2 position
        body.setNextLocation(body.getLocation().add(
                k_positions[1][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.setNextVelocity(body.getVelocity().add(
                k_velocities[1][body.getId()].multiply(STEPSIZE / 2.0))
        );

//        body.update();

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

//        body.update();

        Vector force = PhysicsUtils.allForce(body);

        k_positions[3][body.getId()] = calculateCoordinate(body, STEPSIZE);
        k_velocities[3][body.getId()] = calculateVelocity(body, force, STEPSIZE);
    }

    private static void final_state(Body body) {
        body.setNextLocation(
            body.getLocation().add
            (
                k_positions[0][body.getId()].add
                (
                    k_positions[1][body.getId()].add
                    (
                        k_positions[2][body.getId()].add
                        (
                            k_positions[3][body.getId()]
                        )
                    )
                ).multiply(STEPSIZE / 6.0)
            )
        );

        body.setNextVelocity(
            body.getVelocity().add
            (
                k_velocities[0][body.getId()].add
                (
                    k_velocities[1][body.getId()].add
                        (
                            k_velocities[2][body.getId()].add
                            (
                                k_velocities[3][body.getId()]
                            )
                        )
                ).multiply(STEPSIZE / 6.0)
            )
        );

//        body.update();
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
