package com.mygdx.game.PhysicsEngine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameLogic.FlightLogic;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;

public class CameraUtils {
    static float distFactor = SolarSystem.DIST_FACTOR;
    /** method to keep camera centered at the first probe position(so camera follows the first probe)
     * */
    public static void moveCameraToProbe(OrthographicCamera camera, Vector3 screenCenter){

        if (SolarSystem.probe != null) {
            Vector toFollow = SolarSystem.bodies.get(SystemProperties.PROBE).getLocation(); // our custom vector
            Vector3 toFollow_gdx = new Vector3(screenCenter.x + (float) (toFollow.x / distFactor), screenCenter.y + (float) (toFollow.y / distFactor), 0);

            camera.position.set(toFollow_gdx);
            camera.update();
        }

    }
}
