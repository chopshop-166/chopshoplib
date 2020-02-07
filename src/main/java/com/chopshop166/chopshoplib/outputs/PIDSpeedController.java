package com.chopshop166.chopshoplib.outputs;

/**
 * PIDSpeedController
 * <p>
 * Create an Interface that will act as a sendablespeedcontroller. It will take
 * multiple types of controllers and allow for PID use but will have the
 * additional cappabilities of PID.
 * 
 * @author Andrew Martin
 * @since 2020-02-1
 */

public interface PIDSpeedController {

    public void setP(double kp);

    public void setI(double ki);

    public void setD(double kd);

    public void setSetpoint(double setPoint);

}
