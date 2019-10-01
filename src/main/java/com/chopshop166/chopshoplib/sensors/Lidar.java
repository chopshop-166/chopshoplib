package com.chopshop166.chopshoplib.sensors;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

import com.chopshop166.chopshoplib.SampleBuffer;
import com.google.common.math.Stats;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * LiDAR Sensor class.
 */
public class Lidar extends SendableBase implements PIDSource {
    private I2C i2cDevice;
    private Thread accessThread;
    private double distanceMM;

    private boolean isValid;
    private SampleBuffer samples;

    private double stdDevValue;
    private double stdDevLimit = 100;

    /**
     * The scale to return measurements in.
     */
    public enum MeasurementType {
        INCHES, MILLIMETERS;
    }

    /**
     * Polling loop used by poll thread.
     */
    private void poll() {
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

    /**
     * Settings received from the sensor.
     */
    public static class Settings {
        /**
         * Mode of operation.
         */
        public enum OpMode {
            /** Invalid state. */
            INVALID((byte) 0),
            /** Use pulsed wave. */
            SINGLESTEP((byte) 0x43),
            /** Use continuous wave. */
            CONTINOUS((byte) 0x53);

            final byte value;

            OpMode(final byte value) {
                this.value = value;
            }

            public byte toByte() {
                return value;
            }

            /**
             * Parse from the LiDAR's internal format.
             * 
             * @param value The raw byte to parse.
             * @return The operation mode.
             */
            public static OpMode fromByte(final byte value) {
                return Arrays.stream(values()).filter(v -> v.value == value).findFirst().orElse(INVALID);
            }

            /**
             * Parse the LiDAR's internal format, including bitshifting.
             * 
             * @param value The raw byte to parse.
             * @return The operation mode.
             */
            public static OpMode fromSettingsByte(final byte value) {
                return fromByte(value);
            }
        }

        /**
         * Preset configuration.
         */
        public enum PresetConfiguration {
            /** Custom settings. */
            CUSTOM((byte) 0),
            /** Optimized for high accuracy. */
            HIGHACCURACY((byte) 0x41),
            /** Optimized for long range measurements. */
            LONGRANGE((byte) 0x52),
            /** Optimized for high speed measurements. */
            HIGHSPEED((byte) 0x53),
            /** Preset is a tinyLIDAR. */
            TINYLIDAR((byte) 0x54);

            byte value;

            PresetConfiguration(final byte value) {
                this.value = value;
            }

            public byte toByte() {
                return value;
            }

            /**
             * Parse from the LiDAR's internal format.
             * 
             * @param value The raw byte to parse.
             * @return The preset in use.
             */
            public static PresetConfiguration fromByte(final byte value) {
                return Arrays.stream(values()).filter(conf -> conf.value == value).findFirst().orElse(CUSTOM);
            }

            /**
             * Parse the LiDAR's internal format, including bitshifting.
             * 
             * @param value The raw byte to parse.
             * @return The operation mode.
             */
            public static PresetConfiguration fromSettingsByte(final byte value) {
                return fromByte(value);
            }
        }

        /**
         * The state of the LED indicator.
         */
        public enum LedIndicator {
            /** The light is off. */
            OFF(0),
            /** The light is on. */
            ON(1),
            /** The light is used for measurement. */
            MEASUREMENT(2),
            /** The light is in an unknown state. */
            UNKNOWN(3);

            int value;

            LedIndicator(final int value) {
                this.value = value;
            }

            public int toInt() {
                return value;
            }

            /**
             * Parse from the LiDAR's internal format.
             * 
             * @param value The raw int to parse.
             * @return The LED state.
             */
            public static LedIndicator fromInt(final int value) {
                return Arrays.stream(values()).filter(v -> v.value == value).findFirst().orElse(UNKNOWN);
            }

            /**
             * Parse from the LiDAR's internal format, including bitshifting.
             * 
             * @param value The raw int to parse.
             * @return The LED state.
             */
            public static LedIndicator fromSensorByte(final byte value) {
                return fromInt((value & 0x6) >> 1);
            }
        }

        /**
         * Method of calculating the offset.
         */
        public enum OffsetCalFlag {
            /** Use default calculation. */
            DEFAULT(0),
            /** Use custom calculation. */
            CUSTOM(1);

            int value;

            OffsetCalFlag(final int value) {
                this.value = value;
            }

            public int toInt() {
                return value;
            }

            /**
             * Parse from the LiDAR's internal format.
             * 
             * @param value The raw int to parse.
             * @return The offset calculation being used.
             */
            public static OffsetCalFlag fromInt(final int value) {
                if (value == 0) {
                    return DEFAULT;
                } else {
                    return CUSTOM;
                }
            }

            /**
             * Parse from the LiDAR's internal format, including bitshifting.
             * 
             * @param value The raw int to parse.
             * @return The offset calculation being used.
             */
            public static OffsetCalFlag fromSensorByte(final int value) {
                return fromInt((value & 0x8) >> 3);
            }
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
            offsetCalibration = OffsetCalFlag.fromSensorByte(response[14]);
            ledIndicatorMode = LedIndicator.fromSensorByte(response[14]);
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
        samples = new SampleBuffer(averageOver);

        accessThread = new Thread(this::poll);
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
            samples.reset();
        }
    }

    /**
     * This function gets the distance from a LiDAR sensor
     *
     * @param meas The unit of measure to return in
     * @return An Optional containing the distance if it exists
     */
    public Optional<Double> getDistanceOptional(final MeasurementType meas) {
        synchronized (this) {
            if (!isValid) {
                return Optional.empty();
            }
        }
        return Optional.of(getDistance(meas));
    }

    /**
     * This function gets the distance from a LiDAR sensor
     *
     * @param meas The unit of measure to return in
     * @return The distance
     */
    public double getDistance(final MeasurementType meas) {
        return meas == MeasurementType.INCHES ? distanceMM / 25.4 : distanceMM;
    }

    private void readDistance() {
        final byte[] dataBuffer = new byte[2];

        i2cDevice.write(0x44, 0x1);
        i2cDevice.readOnly(dataBuffer, 2);
        final ByteBuffer bbConvert = ByteBuffer.wrap(dataBuffer);
        synchronized (this) {
            samples.addSample(bbConvert.getShort());
            final Stats stats = Stats.of(samples);
            distanceMM = stats.mean();
            // If the standard deviation is really high then the sensor likely doesn't have
            // a valid reading.
            stdDevValue = stats.populationStandardDeviation();
            isValid = stdDevValue < stdDevLimit;
        }
    }

    /**
     * Change the mode of the LiDAR sensor
     *
     * @param mode Which mode to change to
     */
    public void setMode(final Settings.OpMode mode) {
        final byte modeByte = mode.toByte();
        i2cDevice.writeBulk(new byte[] { 0x4d, modeByte });
    }

    /**
     * Get the settings from the LiDAR.
     * 
     * @return A populated {@link Settings} object.
     */
    public Settings querySettings() {
        final byte[] dataBuffer = new byte[23];
        i2cDevice.writeBulk(new byte[] { 0x51 });
        i2cDevice.readOnly(dataBuffer, 23);
        return new Settings(dataBuffer);
    }

    /**
     * Get the thread used for polling this LiDAR.
     * 
     * @return The {@link Thread} object.
     */
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
            mmDistance.setDouble(getDistance(MeasurementType.MILLIMETERS));
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
        return getDistance(MeasurementType.MILLIMETERS);
    }
}
