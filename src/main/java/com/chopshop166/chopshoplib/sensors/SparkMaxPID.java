package com.chopshop166.chopshoplib.sensors;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.PIDInterface;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * A wrapper for the {@link CANEncoder} provided by REV Robotics, to implement
 * WPIlib interfaces.
 */
public class SparkMaxPID extends SendableBase implements PIDInterface {

    private double setpoint;
    private ControlType controlType;
    private CANPIDController controller;

    /**
     * Create a wrapper object.
     * 
     * @param encoder The encoder to wrap around.
     */
    public SparkMaxPID(final CANPIDController controller) {
        this.controller = controller;
    }

    /**
     * Set the control type of this controller.
     * 
     * @param controlType The control type to use.
     */
    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    /**
     * Get the control type of this controller.
     * 
     * @return The control type.
     */
    public ControlType getControlType() {
        return controlType;
    }

    /**
     * Set the P value.
     * 
     * @param p The proportional value.
     */
    public void setP(double p) {
        controller.setP(p);
    }

    /**
     * Set the I value.
     * 
     * @param i The integral value.
     */
    public void setI(double i) {
        controller.setI(i);
    }

    /**
     * Set the D value.
     * 
     * @param d The derivative value.
     */
    public void setD(double d) {
        controller.setD(d);
    }

    /**
     * Set the F value.
     * 
     * @param f The feed-forward value.
     */
    public void setF(double f) {
        controller.setFF(f);
    }

    /**
     * Get the F value.
     * 
     * @return The feed-forward value.
     */
    public double getF() {
        return controller.getFF();
    }

    @Override
    public void setPID(double p, double i, double d) {
        setP(p);
        setI(i);
        setD(d);
    }

    @Override
    public double getP() {
        return controller.getP();
    }

    @Override
    public double getI() {
        return controller.getI();
    }

    @Override
    public double getD() {
        return controller.getD();
    }

    @Override
    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
        controller.setReference(setpoint, controlType);
    }

    @Override
    public double getSetpoint() {
        return setpoint;
    }

    @Override
    public double getError() {
        return 0.0;
    }

    @Override
    public void reset() {
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PIDController");
        builder.setSafeState(this::reset);
        builder.addDoubleProperty("p", this::getP, this::setP);
        builder.addDoubleProperty("i", this::getI, this::setI);
        builder.addDoubleProperty("d", this::getD, this::setD);
        builder.addDoubleProperty("f", this::getF, this::setF);
        builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
    }

}