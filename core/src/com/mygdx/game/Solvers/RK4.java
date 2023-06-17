package com.mygdx.game.Solvers;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;
import com.mygdx.game.Objects.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class RK4 implements Solver{
    private float STEPSIZE;
    private Function function;
    private Vector[][] k_positions;
    private Vector[][] k_velocities;
    private ArrayList<Body> cloned_universe;
    private ArrayList<Body> init_universe;
    private float initialTime;


    public void calculateNextState(ArrayList<Body> universe, Function givenFunction, float initialTime, float stepSize) {
        //TODO make code compatible of working with more than one probe(may require changing id in Probe class)
        this.STEPSIZE = stepSize;

        cloned_universe = new ArrayList<>();
        init_universe = new ArrayList<>();

        function = givenFunction;

        this.initialTime = initialTime;

        // save initial state of all planets and probes and fill cloned universe
        for (Body body : universe) {
            init_universe.add(body);
            cloned_universe.add(body.clone());
        }

        k_positions = new Vector[4][init_universe.size()];
        k_velocities = new Vector[4][init_universe.size()];

        // calculate k1
        for (Body body : cloned_universe) {
            k1State(body);
        }

        updateUniverse(cloned_universe);

        // calculate k2
        for (Body body : cloned_universe) {
            k2State(body);
        }

        updateUniverse(cloned_universe);

        // calculate k3
        for (Body body : cloned_universe) {
            k3State(body);
        }

        updateUniverse(cloned_universe);

        // calculate k4
        for (Body body : cloned_universe) {
            k4State(body);
        }

        // calculate final new state for planets
        for (Body body: init_universe) {
            final_state(body);
        }
    }

    // k_State methods move clone of our bodies not actual bodies themselves

    /** Calculates k1 state of given body
     * */
    private void k1State(Body body) {
        // evaluate function for coordinate and velocity and write result to relative k arrays
        k_positions[0][body.getId()] = function.calculateCoordinateChange(body, cloned_universe, initialTime);
        k_velocities[0][body.getId()] = function.calculateVelocityChange(body, cloned_universe, initialTime);

        // update location and velocity of a cloned body to Yn + h * k1 / 2 position to calculate force in next position
        body.setNextLocation(init_universe.get(body.getId()).getLocation().add(
                k_positions[0][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.setNextVelocity(init_universe.get(body.getId()).getVelocity().add(
                k_velocities[0][body.getId()].multiply(STEPSIZE / 2.0))
        );
    }

    private void k2State(Body body) {
        k_positions[1][body.getId()] = function.calculateCoordinateChange(body, cloned_universe, initialTime + STEPSIZE / 2.0f);
        k_velocities[1][body.getId()] = function.calculateVelocityChange(body, cloned_universe, initialTime + STEPSIZE / 2.0f);

        // update location and velocity of a cloned body to Yn + h * k2 / 2 position to calculate force in that position
        body.setNextLocation(init_universe.get(body.getId()).getLocation().add(
                k_positions[1][body.getId()].multiply(STEPSIZE / 2.0))
        );

        body.setNextVelocity(init_universe.get(body.getId()).getVelocity().add(
                k_velocities[1][body.getId()].multiply(STEPSIZE / 2.0))
        );
    }

    private void k3State(Body body) {
        k_positions[2][body.getId()] = function.calculateCoordinateChange(body, cloned_universe, initialTime + STEPSIZE / 2.0f);
        k_velocities[2][body.getId()] = function.calculateVelocityChange(body, cloned_universe, initialTime + STEPSIZE / 2.0f);

        // update location and velocity of a cloned body to Yn + h * k3 position to calculate force in that position
        body.setNextLocation(init_universe.get(body.getId()).getLocation().add(
                k_positions[2][body.getId()].multiply(STEPSIZE))
        );

        body.setNextVelocity(init_universe.get(body.getId()).getVelocity().add(
                k_velocities[2][body.getId()].multiply(STEPSIZE))
        );
    }

    private void k4State(Body body) {
        k_positions[3][body.getId()] = function.calculateCoordinateChange(body, cloned_universe, initialTime + STEPSIZE);
        k_velocities[3][body.getId()] = function.calculateVelocityChange(body, cloned_universe, initialTime + STEPSIZE);
    }

    private void final_state(Body body) {
        Vector next_location = k_positions[0][body.getId()]; // k1
        next_location = next_location.add(k_positions[1][body.getId()].multiply(2.0)); // k1 + 2 * k2
        next_location = next_location.add(k_positions[2][body.getId()].multiply(2.0)); // k1 + 2 * k2 + 2 * k3
        next_location = next_location.add(k_positions[3][body.getId()]); // k1 + 2 * k2 + 2 * k3 + k4
        next_location = next_location.multiply(STEPSIZE / 6.0); // h/6 * (k1 + 2 * k2 + 2 * k3 + k4)
        next_location = next_location.add(init_universe.get(body.getId()).getLocation()); // Yn + h/6 * (k1 + 2 * k2 + 2 * k3 + k4)

        body.setNextLocation(next_location);


        Vector next_velocity = k_velocities[0][body.getId()]; // k1
        next_velocity = next_velocity.add(k_velocities[1][body.getId()].multiply(2.0)); // k1 + 2 * k2
        next_velocity = next_velocity.add(k_velocities[2][body.getId()].multiply(2.0)); // k1 + 2 * k2 + 2 * k3
        next_velocity = next_velocity.add(k_velocities[3][body.getId()]); // k1 + 2 * k2 + 2 * k3 + k4
        next_velocity = next_velocity.multiply(STEPSIZE / 6.0); // h/6 * (k1 + 2 * k2 + 2 * k3 + k4)
        next_velocity = next_velocity.add(init_universe.get(body.getId()).getVelocity()); // Yn + h/6 * (k1 + 2 * k2 + 2 * k3 + k4)

        body.setNextVelocity(next_velocity);
    }


    /** Updates whole given universe(each body from it)
     * */
    private void updateUniverse(ArrayList<Body> universeToUpdate){
        for (Body body : universeToUpdate) {
            body.update();
        }
    }
}
