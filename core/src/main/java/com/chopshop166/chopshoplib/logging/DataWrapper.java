package com.chopshop166.chopshoplib.logging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Data object wrapper. */
public class DataWrapper implements LoggableInputs {

    /** The list of objects to log. */
    private final List<LogConfig> logConfig = new ArrayList<>();

    @Override
    public void toLog(final LogTable table) {
        this.getLogValues().forEach(c -> c.toLog(table, this));
    }

    @Override
    public void fromLog(final LogTable table) {
        this.getLogValues().forEach(c -> c.fromLog(table, this));
    }

    private List<LogConfig> getLogValues() {
        // On first run, cache the mapping of field and logname to logger
        if (this.logConfig.isEmpty()) {
            this.logConfig.addAll(Arrays.stream(this.getClass().getFields())
                    .filter(f -> f.getAnnotation(NoLog.class) == null)
                    .filter(DataWrapper::fieldTypeIsLoggable).map(LogConfig::fromField)
                    .filter(c -> c != null).toList());
        }
        return this.logConfig;
    }

    private static boolean fieldTypeIsLoggable(final Field field) {
        final Class<?> fieldType = field.getType();
        return LoggableInputs.class.isAssignableFrom(fieldType) || fieldType.isEnum()
                || FieldLogger.BOXABLE_CLASSES.containsKey(fieldType);
    }

}
