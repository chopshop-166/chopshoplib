package com.chopshop166.chopshoplib.logging;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;
import com.chopshop166.chopshoplib.motors.PIDControlType;
import com.chopshop166.chopshoplib.states.LinearDirection;
import com.chopshop166.chopshoplib.states.OpenClose;
import com.chopshop166.chopshoplib.states.SpinDirection;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;

/** Logger wrapper for a given field type. */
/* package */ abstract class FieldLogger {

    /** The classes that are able to be logged. */
    public final static Map<Class<?>, FieldLogger> BOXABLE_CLASSES = new HashMap<>();
    /** The logger to use for enums. */
    public final static FieldLogger ENUM_LOGGER = new FieldLogger() {
        @Override
        public void toLog(final String name, final LogTable table, final Field field,
                final Object that) throws IllegalAccessException {
            table.put(name, field.get(that).toString());
        }

        @Override
        public void fromLog(final String name, final LogTable table, final Field field,
                final Object that) throws IllegalAccessException {
            final String fieldValueStr = field.get(that).toString();
            final String newTableValue = table.get(name, fieldValueStr);
            for (final var constant : field.getType().getEnumConstants()) {
                if (constant.toString().equals(newTableValue)) {
                    field.set(that, constant);
                }
            }
        }
    };

    /**
     * Create the logger to use for an enum type.
     * 
     * @param clazz The class descriptor.
     */
    public static <T extends Enum<T>> void registerEnumForLogger(final Class<T> clazz) {
        BOXABLE_CLASSES.putIfAbsent(clazz, new FieldLogger() {
            @Override
            @SuppressWarnings("unchecked")
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (T) field.get(that));
            }

            @Override
            @SuppressWarnings("unchecked")
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                final T fieldValue = (T) field.get(that);
                final T newTableValue = table.get(name, fieldValue);
                field.set(that, newTableValue);
            }
        });
    }

    /**
     * Convert a field to a log.
     * 
     * @param name The name of the log.
     * @param table The table to log into.
     * @param field The field to log.
     * @param that The object to log from.
     * @throws IllegalAccessException Only if something's horribly wrong
     */
    abstract public void toLog(String name, LogTable table, Field field, Object that)
            throws IllegalAccessException;

    /**
     * Convert a field to a log.
     * 
     * @param name The name of the log.
     * @param table The table to read from.
     * @param field The field to set.
     * @param that The object to set to.
     * @throws IllegalAccessException Only if something's horribly wrong
     */
    abstract public void fromLog(String name, LogTable table, Field field, Object that)
            throws IllegalAccessException;

    static {
        BOXABLE_CLASSES.put(Boolean.TYPE, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, field.getBoolean(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.setBoolean(that, table.get(name, field.getBoolean(that)));
            }
        });
        BOXABLE_CLASSES.put(boolean[].class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (boolean[]) field.get(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.set(that, table.get(name, (boolean[]) field.get(that)));
            }
        });
        BOXABLE_CLASSES.put(Double.TYPE, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, field.getDouble(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.setDouble(that, table.get(name, field.getDouble(that)));
            }
        });
        BOXABLE_CLASSES.put(double[].class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (double[]) field.get(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.set(that, table.get(name, (double[]) field.get(that)));
            }
        });
        BOXABLE_CLASSES.put(Integer.TYPE, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, field.getInt(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.setInt(that, table.get(name, field.getInt(that)));
            }
        });
        BOXABLE_CLASSES.put(int[].class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (int[]) field.get(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.set(that, table.get(name, (int[]) field.get(that)));
            }
        });
        BOXABLE_CLASSES.put(Long.TYPE, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, field.getLong(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.setLong(that, table.get(name, field.getLong(that)));
            }
        });
        BOXABLE_CLASSES.put(int[].class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (int[]) field.get(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.set(that, table.get(name, (int[]) field.get(that)));
            }
        });
        BOXABLE_CLASSES.put(String.class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (String) field.get(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.set(that, table.get(name, (String) field.get(that)));
            }
        });
        BOXABLE_CLASSES.put(String[].class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, (String[]) field.get(that));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                field.set(that, table.get(name, (String[]) field.get(that)));
            }
        });
        BOXABLE_CLASSES.put(LoggableInputs.class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                final LoggableInputs inps = (LoggableInputs) field.get(that);
                inps.toLog(table.getSubtable(name));
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                final LoggableInputs inps = (LoggableInputs) field.get(that);
                inps.fromLog(table.getSubtable(name));
            }
        });
        BOXABLE_CLASSES.put(Rotation2d.class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                table.put(name, ((Rotation2d) field.get(that)).getDegrees());
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                final double degrees = table.get(name, ((Rotation2d) field.get(that)).getDegrees());
                field.set(that, Rotation2d.fromDegrees(degrees));
            }
        });
        BOXABLE_CLASSES.put(Rotation3d.class, new FieldLogger() {
            @Override
            public void toLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                final Rotation3d rot = (Rotation3d) field.get(that);
                table.put(name, new double[] {rot.getX(), rot.getY(), rot.getZ()});
            }

            @Override
            public void fromLog(final String name, final LogTable table, final Field field,
                    final Object that) throws IllegalAccessException {
                final double[] rotData = table.get(name, new double[3]);
                field.set(that, new Rotation3d(rotData[0], rotData[1], rotData[2]));
            }
        });
        // Add extractors for the common enums we use
        registerEnumForLogger(ButtonXboxController.POVDirection.class);
        registerEnumForLogger(DriverStation.Alliance.class);
        registerEnumForLogger(DriverStation.MatchType.class);
        registerEnumForLogger(DoubleSolenoid.Value.class);
        registerEnumForLogger(LinearDirection.class);
        registerEnumForLogger(OpenClose.class);
        registerEnumForLogger(PIDControlType.class);
        registerEnumForLogger(PneumaticsModuleType.class);
        registerEnumForLogger(SpinDirection.class);
        registerEnumForLogger(RobotDriveBase.MotorType.class);
        registerEnumForLogger(XboxController.Axis.class);
        registerEnumForLogger(XboxController.Button.class);
    }

}
