package com.mygdx.game;

import java.util.HashMap;
import java.util.Hashtable;

public class SystemProperties {
    final public static Hashtable<String, Integer> entities = new Hashtable<String, Integer>(){
        {put("Sun", 0); put("Mercury", 1)}
    };
    final public static Vector[] coordinates = new Vector[]{new Vector(0.0, 0.0, 0.0)};
    final public static Vector[] velocities = new Vector[]{new Vector(0.0, 0.0, 0.0)};
    final public static double[] masses = new double[]{1.9885e30};
    final public static double[] radiuses = new double[]{0};
}
