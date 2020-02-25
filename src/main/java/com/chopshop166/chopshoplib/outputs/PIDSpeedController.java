package com.chopshop166.chopshoplib.outputs;

/**
 * PIDSpeedController
 * <p>
 * Create an Interface that will act as a sendablespeedcontroller. It will take
 * multiple types of controllers and allow for PID use, but will have the
 * additional capabilities of PID.
 * 
 * @author Andrew Martin
 * @since 2020-02-1
 */

public interface PIDSpeedController extends SendableSpeedController {

    void setP(double kp);

    void setI(double ki);

    void setD(double kd);

    default void setF(double kf) {
        // Ignore feed-forward by default
    }

    void setSetpoint(double setPoint);

}
