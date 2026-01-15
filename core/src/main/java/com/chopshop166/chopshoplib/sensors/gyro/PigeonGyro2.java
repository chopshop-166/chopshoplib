package com.chopshop166.chopshoplib.sensors.gyro;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;

/**
 * Gyro Base wrapper for the Pigeon IMU
 */
public class PigeonGyro2 implements SmartGyro {

    /** The wrapped object. */
    private final Pigeon2 gyro;

    /**
     * Create the wrapper.
     *
     * @param gyro The object to wrap.
     */
    public PigeonGyro2(final Pigeon2 gyro) {
        super();
        this.gyro = gyro;
    }

    /**
     * Create the wrapper.
     *
     * @param deviceNumber The CAN ID of the Pigeon 2.
     */
    public PigeonGyro2(final int deviceNumber) {
        this(new Pigeon2(deviceNumber));
    }

    /**
     * Create the wrapper.
     *
     * @param deviceNumber The CAN ID of the Pigeon 2.
     * @param canBus The CAN Bus name.
     */
    public PigeonGyro2(final int deviceNumber, final String canBus) {
        this(new Pigeon2(deviceNumber, CANBus.roboRIO(canBus)));
    }


    /**
     * Get the wrapped object.
     *
     * @return The wrapped class.
     */
    public Pigeon2 getRaw() {
        return this.gyro;
    }

    /**
     * Sets the gyro's heading back to zero
     */
    @Override
    public void reset() {
        this.gyro.setYaw(0);
    }

    /**
     * @return The rate of rotation of the gyro.
     */
    @Override
    public double getRate() {
        return -this.gyro.getAngularVelocityZWorld().getValueAsDouble();
    }

    /**
     * @return The current angle of the gyro
     */
    @Override
    public double getAngle() {
        return -this.gyro.getYaw().getValueAsDouble();
    }

    @Override
    public void setAngle(final double angleDeg) {
        this.gyro.setYaw(angleDeg);
    }

    @Override
    public Rotation2d getRotation2d() {
        return this.gyro.getRotation2d();
    }

    @Override
    public Rotation3d getRotation3d() {
        return new Rotation3d(Units.degreesToRadians(this.gyro.getRoll().getValueAsDouble()),
                Units.degreesToRadians(this.gyro.getPitch().getValueAsDouble()),
                Units.degreesToRadians(this.gyro.getYaw().getValueAsDouble()));
    }

    @Override
    public Rotation3d getRotationalVelocity() {
        return new Rotation3d(
                Units.degreesToRadians(this.gyro.getAngularVelocityXWorld().getValueAsDouble()),
                Units.degreesToRadians(this.gyro.getAngularVelocityYWorld().getValueAsDouble()),
                Units.degreesToRadians(this.gyro.getAngularVelocityZWorld().getValueAsDouble()));
    }
}
