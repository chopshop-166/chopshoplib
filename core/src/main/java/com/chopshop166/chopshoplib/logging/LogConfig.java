package com.chopshop166.chopshoplib.logging;

import java.lang.reflect.Field;
import org.littletonrobotics.junction.LogTable;

/* package */ record LogConfig(String name, Field field, FieldLogger logger) {
    /**
     * Create from a single field, checking annotations.
     * 
     * @param field The field to use.
     * @return A new object.
     */
    public static LogConfig fromField(final Field field) {
        // Get the name that it gets logged by
        String name = field.getName();
        if (field.isAnnotationPresent(LogName.class)) {
            name = field.getAnnotation(LogName.class).value();
        }
        // Figure out which logger goes with that value
        FieldLogger logger = null;
        for (final var entry : FieldLogger.BOXABLE_CLASSES.entrySet()) {
            if (entry.getKey().isAssignableFrom(field.getType())) {
                logger = entry.getValue();
                break;
            }
        }
        // If we didn't special-case the enum, then fall back to the string method
        if (logger == null && field.getType().isEnum()) {
            logger = FieldLogger.ENUM_LOGGER;
        }
        return logger == null ? null : new LogConfig(name, field, logger);
    }

    /**
     * Log the field to the given table.
     * 
     * @param table A logging table.
     */
    public void toLog(final LogTable table, final Object that) {
        try {
            this.logger.toLog(this.name, table, this.field, that);
        } catch (IllegalAccessException ex) {
            // Silently fail
        }
    }

    /**
     * Read the field from the given table.
     * 
     * @param table A logging table.
     */
    public void fromLog(final LogTable table, final Object that) {
        try {
            this.logger.fromLog(this.name, table, this.field, that);
        } catch (IllegalAccessException ex) {
            // Silently fail
        }
    }
}
