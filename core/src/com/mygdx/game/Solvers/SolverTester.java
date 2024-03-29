package com.mygdx.game.Solvers;

import java.util.ArrayList;
import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Objects.celestialBody;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.SupportiveClasses.DataReader;
import com.mygdx.game.SupportiveClasses.Timer;



public class SolverTester {
    private final static Timer timer = new Timer(4, 0.5f, 2023, 4, 1);

    public static void main(String[] args) {
        //manually calculate rk4 approximation for specified function and compare results
        DataReader reader = new DataReader();
        reader.readFlightScene();

        ArrayList<Body> universe = new ArrayList<>();

        double initialValue = 0.0;

        Body body = new celestialBody(0);
        body.setLocation(new Vector(initialValue, initialValue, initialValue));
        body.setVelocity(new Vector(initialValue, initialValue, initialValue));

        body.setNextLocation(new Vector(initialValue, initialValue,initialValue));
        body.setNextLocation(new Vector(initialValue, initialValue, initialValue));


        universe.add(body);

        Function function = new Func();

        Solver solver = new RK4();

        System.out.println(body.getLocation());

        while (!timer.isTimeReached()) {
            solver.calculateNextState(universe, function, timer.getTimePassed(), timer.stepSize);

            timer.iterate();

            for (Body obj : universe) {
                obj.update();

                System.out.println(timer.getTimePassed());
                System.out.println(obj.getLocation());
            }
        }
    }
}
