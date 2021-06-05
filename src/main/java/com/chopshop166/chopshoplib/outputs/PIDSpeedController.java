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
public interface PIDSpeedController extends SmartSpeedController {

    /**
     * Set the proportional coefficient.
     * 
     * @param kp The coefficient.
     */
    void setP(double kp);

    /**
     * Set the integral coefficient.
     * 
     * @param ki The coefficient.
     */
    void setI(double ki);

    /**
     * Set the derivative coefficient.
     * 
     * @param kd The coefficient.
     */
    void setD(double kd);

    /**
     * Set the feed-forward coefficient.
     * 
     * @param kf The coefficient.
     */
    default void setF(final double kf) {
        // Ignore feed-forward by default
    }

    /**
     * Set the setpoint.
     * 
     * @param setPoint The new setpoint.
     */
    void setSetpoint(double setPoint);

}
