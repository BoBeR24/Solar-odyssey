package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Properties.SolarSystem;
import com.mygdx.game.Properties.SystemProperties;

/**
 * Class representing all bodies in simulated system
 */
public class celestialBody implements Body {
    private final int id; // id which represents position in the list of bodies
    private final int radius;
    private final double mass;
    private Vector location;
    private Vector nextLocation; // location to which planet will be moved in the next state of the system
    private Vector velocity;
    private Vector nextVelocity; // velocity of the current object in the next state of the system
    private Color color; // color of the body
    private float width; // width of the image of the body
    private float height; // height of the image of the body

    /**
     * Class representing celestial bodies(planets) with all their common properties
     *
     * @param index - index of a planet this particular instance represents
     */
    public celestialBody(int index) {
        this.id = index;

        this.color = Color.WHITE; // default color

        this.location = new Vector(SystemProperties.initCoordinates[id]);
        this.velocity = new Vector(SystemProperties.initVelocities[id]);

        // let next states initially be same as current(they will be changed anyway)
        this.nextLocation = new Vector(this.location);
        this.nextVelocity = new Vector(this.velocity);

        this.radius = SystemProperties.radii[id];

        this.mass = SystemProperties.masses[id];

        // if object id refers to the Sun scaling factor differs, to avoid sun being too big
        if (this.id == 0) {
            this.width = SystemProperties.radii[0] / (SolarSystem.SIZE_FACTOR * 8); // default width and height
            this.height = SystemProperties.radii[0] / (SolarSystem.SIZE_FACTOR * 8);

        } else {
            this.width = SystemProperties.radii[id] / SolarSystem.SIZE_FACTOR; // default width and height
            this.height = SystemProperties.radii[id] / SolarSystem.SIZE_FACTOR;
        }
    }


    /**
     * Returns a clone of this body(clone is not full, though, as it only takes current state and next state of the object,
     * but not any additional properties)
     * example of cloning Earth:
     * Body new_body = SolarSystem.planets.get(SystemProperties.EARTH).clone();
     *
     * @see Body#clone()
     */
    @Override
    public celestialBody clone() {
        celestialBody cloned_body = new celestialBody(this.id);

        cloned_body.setLocation(new Vector(this.location));
        cloned_body.setVelocity(new Vector(this.velocity));

        cloned_body.setNextLocation(new Vector(this.nextLocation));
        cloned_body.setNextVelocity(new Vector(this.nextVelocity));

        return cloned_body;
    }

    /**
     * implementation of an update method for celestial bodies(planets)
     *
     * @see Body#update()
     */
    @Override
    public void update() {
        this.setLocation(this.nextLocation);
        this.setVelocity(this.nextVelocity);
    }

    @Override
    public void setLocation(double x, double y, double z) {
        this.location.x = x;
        this.location.y = y;
        this.location.z = z;
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.z = z;
    }

    @Override
    public void setNextLocation(double x, double y, double z) {
        this.nextLocation.x = x;
        this.nextLocation.y = y;
        this.nextLocation.z = z;
    }

    @Override
    public void setNextVelocity(double x, double y, double z) {
        this.nextVelocity.x = x;
        this.nextVelocity.y = y;
        this.nextVelocity.z = z;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Vector getLocation() {
        return this.location;
    }

    public void setLocation(Vector vector) {
        this.setLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector vector) {
        this.setVelocity(vector.x, vector.y, vector.z);
    }

    @Override
    public Vector getNextLocation() {
        return this.nextLocation;
    }

    public void setNextLocation(Vector vector) {
        this.setNextLocation(vector.x, vector.y, vector.z);
    }

    @Override
    public Vector getNextVelocity() {
        return this.nextVelocity;
    }

    public void setNextVelocity(Vector vector) {
        this.setNextVelocity(vector.x, vector.y, vector.z);
    }

    @Override
    public double getMass() {
        return this.mass;
    }

    public double getRadius() {
        return this.radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }


}