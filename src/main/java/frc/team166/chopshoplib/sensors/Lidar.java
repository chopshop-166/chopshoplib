package frc.team166.chopshoplib.sensors;

import java.nio.ByteBuffer;
import java.util.Optional;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class Lidar extends SensorBase implements PIDSource {
    private I2C i2cDevice;
    private Thread accessThread;
    private double distanceMM;

    private boolean isValid;
    private double samples[];
    private int sampleIndex;
    private boolean isReset;

    private final StandardDeviation stdDev = new StandardDeviation();
    private double stdDevValue;
    private double stdDevLimit = 100;

    private class PollSensor implements Runnable {

        public PollSensor() {
            super();
        }

        @Override
        public void run() {
            while (true) {
                /* Get the distance from the sensor */
                readDistance();
                /* Sensor updates at 60Hz, but we'll run this at 50 since the math is nicer */
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    /* We stop for nothing! */
                }
            }
        }
    }

    public static class Settings {
        public enum OpMode {
            SINGLESTEP, CONTINOUS, INVALID;

            public static OpMode fromByte(final byte value) {
                if (value == 0x43) {
                    return CONTINOUS;
                } else if (value == 0x53) {
                    return SINGLESTEP;
                } else {
                    return INVALID;
                }
            }
        }

        public enum PresetConfiguration {
            HIGHSPEED, LONGRANGE, HIGHACCURACY, TINYLIDAR, CUSTOM;

            public static PresetConfiguration fromByte(final byte value) {
                switch (value) {
                case 0x53:
                    return HIGHSPEED;
                case 0x52:
                    return LONGRANGE;
                case 0x41:
                    return HIGHACCURACY;
                case 0x54:
                    return TINYLIDAR;
                default:
                    return CUSTOM;
                }
            }
        }

        public enum LedIndicator {
            OFF, ON, MEASUREMENT, UNKNOWN;

            public static LedIndicator fromInt(final int value) {
                switch (value) {
                case 0:
                    return OFF;
                case 1:
                    return ON;
                case 2:
                    return MEASUREMENT;
                default:
                    return UNKNOWN;
                }
            }
        }

        public enum OffsetCalFlag {
            CUSTOM, DEFAULT;
        }

        public OpMode operationMode;
        public PresetConfiguration preset;
        public double signalRateLimit;
        public int sigmaEstimateLimate;
        public int timingBudgetInMS;
        public int preRangeVcselPeriod;
        public int finalRangeVcselPeriod;
        public String fwVersion;
        public String stPalApi;
        public OffsetCalFlag offsetCalibration;
        public LedIndicator ledIndicatorMode;
        public boolean watchdogTimer;
        public int offsetCalibrationValue;
        public int crosstalkCalibrationValue;

        /**
         * This will process the response from a settings query.
         *
         * This will process the byte array and turn it into a more easily accessible
         * object.
         *
         * @param response A byte array with the response from a settings query
         */
        public Settings(final byte[] response) {
            /* Process the zeroth byte */
            operationMode = OpMode.fromByte(response[0]);
            /* Process the first byte */
            preset = PresetConfiguration.fromByte(response[1]);
            /* Process the 2nd & 3rd bytes */
            signalRateLimit = ByteBuffer.wrap(response, 2, 2).getShort() / 65536.0;
            /* Process the 4th byte */
            sigmaEstimateLimate = response[4];
            /* Process the 5th & 6th bytes */
            timingBudgetInMS = ByteBuffer.wrap(response, 5, 2).getShort();
            /* Process the 7th byte */
            if (response[7] == 0x0e) {
                preRangeVcselPeriod = 14;
                finalRangeVcselPeriod = 10;
            } else if (response[7] == 0x12) {
                preRangeVcselPeriod = 18;
                finalRangeVcselPeriod = 14;
            }
            /* Process the 8th, 9th & 10th bytes */
            fwVersion = String.format("%d.%d.%d", response[8], response[9], response[10]);
            /* Process the 11th, 12th, & 13th bytes */
            stPalApi = String.format("%d.%d.%d", response[11], response[12], response[13]);
            /* Process the 14th byte */
            if (((response[14] >> 3) & 1) == 0) {
                offsetCalibration = OffsetCalFlag.DEFAULT;
            } else {
                offsetCalibration = OffsetCalFlag.CUSTOM;
            }
            ledIndicatorMode = LedIndicator.fromInt((response[14] & 0x6) >> 1);
            watchdogTimer = (response[14] & 1) != 0;
            /* Process the 15th, 16th, 17th, & 18th bytes */
            offsetCalibrationValue = ByteBuffer.wrap(response, 15, 4).getInt() / 1000;
            /* Process the 19th, 20th, 21th, & 22th bytes */
            crosstalkCalibrationValue = ByteBuffer.wrap(response, 19, 4).getInt() / 65536;
        }

    }

    /**
     * Create a LIDAR object
     *
     * @param port        The I2C port the sensor is connected to
     * @param kAddress    The I2C address the sensor is found at
     * @param averageOver The number of samples to average
     */
    public Lidar(final Port port, final int kAddress, final int averageOver) {
        super();
        i2cDevice = new I2C(port, kAddress);
        setName("Lidar", kAddress);

        // Objects related to statistics
        samples = new double[averageOver];

        accessThread = new Thread(new PollSensor());
        accessThread.setName(String.format("LiDAR-0x%x", kAddress));
        accessThread.start();
    }

    /**
     * Create a LIDAR object
     *
     * @param port     The I2C port the sensor is connected to
     * @param kAddress The I2C address the sensor is found at
     */
    public Lidar(final Port port, final int kAddress) {
        // Default to averaging over 25 samples
        this(port, kAddress, 25);
    }

    /**
     * Set the maximum allowed standard deviation before the input is considered
     * invalid
     *
     * @param sdLimit The maximum standard deviation expected
     */
    public void setStandardDeviationLimit(final double sdLimit) {
        synchronized (this) {
            stdDevLimit = sdLimit;
        }
    }

    /**
     * Clear the samples
     */
    public void reset() {
        synchronized (this) {
            for (int i = 0; i < samples.length; i++) {
                samples[i] = 0;
            }
            sampleIndex = 0;
            isReset = true;
        }
    }

    /**
     * This function gets the distance from a LiDAR sensor
     *
     * @param bFlag True requests the distance in inches, false requests the
     *              distance in mm
     */
    public Optional<Double> getDistanceOptional(final Boolean bFlag) {
        synchronized (this) {
            if (!isValid) {
                return Optional.empty();
            }
        }
        return Optional.of(getDistance(bFlag));
    }

    /**
     * This function gets the distance from a LiDAR sensor
     *
     * @param bFlag True requests the distance in inches, false requests the
     *              distance in mm
     */
    public double getDistance(final Boolean bFlag) {
        return bFlag ? distanceMM / 25.4 : distanceMM;
    }

    private void readDistance() {
        final byte[] dataBuffer = new byte[2];

        i2cDevice.write(0x44, 0x1);
        i2cDevice.readOnly(dataBuffer, 2);
        final ByteBuffer bbConvert = ByteBuffer.wrap(dataBuffer);
        synchronized (this) {
            samples[sampleIndex] = bbConvert.getShort();
            sampleIndex++;
            if (sampleIndex == samples.length) {
                isReset = false;
                sampleIndex = 0;
            }
            distanceMM = new Mean().evaluate(samples, 0, isReset ? sampleIndex : samples.length);
            // If the standard deviation is really high then the sensor likely doesn't have
            // a valid reading.
            stdDevValue = stdDev.evaluate(samples, 0, isReset ? sampleIndex : samples.length);
            if (stdDevValue >= stdDevLimit) {
                isValid = false;
            } else {
                isValid = true;
            }
        }
    }

    /**
     * Change the mode of the LiDAR sensor
     *
     * @param mode Which mode to change to
     */
    public void setMode(final Settings.OpMode mode) {
        if (mode == Settings.OpMode.CONTINOUS) {
            i2cDevice.writeBulk(new byte[] { 0x4d, 0x43 });
        } else if (mode == Settings.OpMode.SINGLESTEP) {
            i2cDevice.writeBulk(new byte[] { 0x4d, 0x53 });
        }
    }

    public Settings querySettings() {
        final byte[] dataBuffer = new byte[23];
        i2cDevice.writeBulk(new byte[] { 0x51 });
        i2cDevice.readOnly(dataBuffer, 23);
        return new Settings(dataBuffer);
    }

    public Thread getAccessThread() {
        return accessThread;
    }

    @Override
    public void initSendable(final SendableBuilder builder) {
        builder.setSmartDashboardType("LiDAR");
        final NetworkTableEntry mmDistance = builder.getEntry("Distance");
        final NetworkTableEntry standardDeviation = builder.getEntry("Standard Deviation");
        final NetworkTableEntry validityEntry = builder.getEntry("isValid");
        builder.setUpdateTable(() -> {
            mmDistance.setDouble(getDistance(true));
            synchronized (this) {
                validityEntry.setBoolean(isValid);
                standardDeviation.setDouble(stdDevValue);
            }
        });
    }

    @Override
    public void setPIDSourceType(final PIDSourceType pidSource) {
        if (pidSource != PIDSourceType.kDisplacement) {
            throw new IllegalArgumentException("Only displacement is supported");
        }
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return getDistance(true);
    }
}
