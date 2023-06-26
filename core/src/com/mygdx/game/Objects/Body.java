package com.mygdx.game.Objects;

/** interface which is holds common functionality for different space objects(for example both
 *  planet and probe are considered to be a body)
 * */
public interface Body {
    void setLocation(double x, double y, double z);
    void setLocation(Vector vector);
    void setVelocity(double x, double y, double z);
    void setVelocity(Vector vector);
    void setNextLocation(double x, double y, double z);
    void setNextLocation(Vector vector);
    void setNextVelocity(double x, double y, double z);
    void setNextVelocity(Vector vector);

    Vector getLocation();

    Vector getVelocity();
    Vector getNextLocation();
    Vector getNextVelocity();

    double getMass();
    int getId();

    /** returns independent clone of the object in its current state
     * @return cloned object
     * */
    Body clone();

    /** method which updates object to the next state
     * */
    void update();
    /** method which updates all objects of this type to the next state
     * */
}
