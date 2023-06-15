package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;

import java.util.ArrayList;

public interface Function {
    Vector evaluate(Body body, ArrayList<Body> universe, float time);
}
