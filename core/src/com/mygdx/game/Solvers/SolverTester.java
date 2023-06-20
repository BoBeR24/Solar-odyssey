package com.mygdx.game.Solvers;

import java.util.ArrayList;
import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Objects.celestialBody;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.SupportiveClasses.DataReader;
import com.mygdx.game.SupportiveClasses.Timer;



public class SolverTester {
    private final static Timer timer = new Timer(4, 0.5f);

    public static void main(String[] args) {
        //TODO manually calculate rk4 approximation for specified function and compare results
        // (it is better to recheck correctness of the code again as it behaves weirdly)
        DataReader reader = new DataReader();
        reader.read();

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
            System.out.println(timer.getTimePassed());
            solver.calculateNextState(universe, function, timer.getTimePassed(), timer.stepSize);

            timer.iterate();

            for (Body obj : universe) {
                obj.update();

                System.out.println(obj.getLocation());
            }
        }
    }
}
