package com.mygdx.game;

/** interface which is holds common functionality for different space objects(for example both
 *  planet and probe is considered to be a body)
 * */
public interface Body {
    void setLocation(double x, double y, double z);
    void setVelocity(double x, double y, double z);
    Vector getLocation();

    Vector getVelocity();

    double getMass();
    int getId();
//    String getName();

}
