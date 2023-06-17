package com.mygdx.game.PhysicsEngine;

import com.mygdx.game.Objects.Body;
import com.mygdx.game.Objects.Vector;

import java.util.ArrayList;

public interface Function {
    /** method needed to calculate coordinate function value for a given body in the specified time(to get next location result of this function
     * should be added to previous position of the body)
     * @param body - object for which coordinate is calculated
     * @param universe - system in which given body exists
     * @param time - value representing time at which function is called
     * */
    Vector calculateCoordinateChange(Body body, ArrayList<Body> universe, float time);

    /** method needed to calculate velocity function value for a given body in the specified time(to get next location result of this function
     * should be added to previous velocity of the body)
     * @param body - object for which velocity is calculated
     * @param universe - system in which given body exists
     * @param time - value representing time at which function is called
     * */
    Vector calculateVelocityChange(Body body, ArrayList<Body> universe, float time);
}
