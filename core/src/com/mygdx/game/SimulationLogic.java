package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Odyssey;
import com.mygdx.game.SolarSystem;

public class SimulationLogic {
    private Odyssey game;
    private final double scaleFactor = SolarSystem.scaleFactor; // pre-calculated scaling factor
    private final Vector3 centerScreenCords;




    public SimulationLogic(final Odyssey game) {
        this.game = game;

        this.centerScreenCords = new Vector3((Gdx.graphics.getWidth() - 200) / 2.0f ,
                (Gdx.graphics.getHeight() - 200) / 2.0f, 0);

    }
    public void update(){

        for (celestialBody body : SolarSystem.bodies) {
            game.shape.setColor(body.getColor());
            game.shape.ellipse((float) (centerScreenCords.x + (body.getLocation().x / scaleFactor) - (body.getWidth() / 2)), (float) (centerScreenCords.y + (body.getLocation().y / scaleFactor) - (body.getHeight()) / 2), body.getWidth(), body.getHeight());

        }

            //  adding the Sun
//            game.shape.setColor(Color.YELLOW);
//            game.shape.ellipse(centerScreenCords.x, centerScreenCords.y, 218, 218);
//
//            // adding Mercury
//            game.shape.setColor(Color.valueOf("#C0C2C9"));
//            game.shape.ellipse((float) (centerScreenCords.x + (mercury.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (mercury.getLocation().y / scaleFactor)), 12, 12);
//
//            // adding Venus
//            game.shape.setColor(Color.valueOf("#f7e4ab"));
//            game.shape.ellipse((float) (centerScreenCords.x + (venus.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (venus.getLocation().y / scaleFactor)), 30, 30);
//
//            // adding Earth
//            game.shape.setColor(Color.BLUE);
//            game.shape.ellipse((float) (centerScreenCords.x + (earth.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (earth.getLocation().y / scaleFactor)), 32, 32);
//
//            // adding Moon
//            game.shape.setColor(Color.WHITE);
//            game.shape.ellipse((float) (centerScreenCords.x + (moon.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (moon.getLocation().y / scaleFactor)), 8, 8);
//
//            // adding Mars
//            game.shape.setColor(Color.valueOf("#D6723B"));
//            game.shape.ellipse((float) (centerScreenCords.x + (mars.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (mars.getLocation().y / scaleFactor)), 17, 17);
//
//            // adding Jupiter(this thing is huge)
//            game.shape.setColor(Color.valueOf("#c99039"));
//            game.shape.ellipse((float) (centerScreenCords.x + (jupiter.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (jupiter.getLocation().y / scaleFactor)), 351, 351);
//
//            // adding Saturn
//            game.shape.setColor(Color.valueOf("#ae8b0c"));
//            game.shape.ellipse((float) (centerScreenCords.x + (saturn.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (saturn.getLocation().y / scaleFactor)), 292, 292);
//
//            // adding Titan
//            game.shape.setColor(Color.valueOf("#f2a900"));
//            game.shape.ellipse((float) (centerScreenCords.x + (titan.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (titan.getLocation().y / scaleFactor)), 13, 13);
//
//            // adding Uranus
//            game.shape.setColor(Color.valueOf("#d1e7e7"));
//            game.shape.ellipse((float) (centerScreenCords.x + (uranus.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (uranus.getLocation().y / scaleFactor)), 128, 128);
//
//            // adding Neptune
//            game.shape.setColor(Color.valueOf("#5b5ddf"));
//            game.shape.ellipse((float) (centerScreenCords.x + (neptune.getLocation().x / scaleFactor)), (float) (centerScreenCords.y + (neptune.getLocation().y / scaleFactor)), 128, 128);

    }

    public void close(){ // for disposing all visual elements
//        for (celestialBody body : SolarSystem.bodies) {
//        }
        System.out.println("Thank you");
    }
}
