package com.mygdx.game.Solvers;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.PhysicsEngine.Function;
import com.mygdx.game.PhysicsEngine.PhysicsUtils;

import java.util.ArrayList;

public class Func implements Function {
    // 3^(-t) - y
    @Override
    public Vector calculateCoordinateChange(Body body, ArrayList<Body> universe, float time) {
//        Vector vector = (new Vector(1.0, 1.0, 1.0)).multiply(Math.pow(3, -1 * time));
        Vector vector = new Vector(Math.pow(3, -1 * time), Math.pow(3, -1 * time), Math.pow(3, -1 * time));

//        System.out.println(time + " " +vector.subtract(body.getLocation()));

        return vector.subtract(body.getLocation());
    }

    @Override
    public Vector calculateVelocityChange(Body body, ArrayList<Body> universe, float time) {
        Vector vector = (new Vector(1.0, 1.0, 1.0)).multiply(Math.pow(3, -1 * time));

        return vector.subtract(body.getVelocity());
    }
}
