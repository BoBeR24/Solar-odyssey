package com.mygdx.game;

public class Launch {
    public static Vector launch(celestialBody body, Vector relativeV){
        return relativeV.add(body.getVelocity());
    }
}
