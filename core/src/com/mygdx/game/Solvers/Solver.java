package com.mygdx.game.Solvers;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.PhysicsEngine.Function;

import java.util.ArrayList;

public interface Solver {
    void calculateNextState(ArrayList<Body> universe, Function function, float initialTime, float stepSize);
}
