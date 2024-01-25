package com.chopshop166.chopshoplib.logging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

/** Data object wrapper. */
public class DataWrapperBase implements LoggableInputs {

    /** The list of objects to log. */
    private final List<LogConfig> logConfig = new ArrayList<>();

    @Override
    public void toLog(final LogTable table) {
        final LogTable subtable = this.getTableFrom(table);
        this.getLogValues().forEach(c -> c.toLog(subtable, this));
    }

    @Override
    public void fromLog(final LogTable table) {
        final LogTable subtable = this.getTableFrom(table);
        this.getLogValues().forEach(c -> c.fromLog(subtable, this));
    }

    /**
     * Get the table that this wrapper should log to.
     * 
     * @param table The base table.
     * @return The logging table.
     */
    protected LogTable getTableFrom(final LogTable table) {
        return table;
    }

    private List<LogConfig> getLogValues() {
        // On first run, cache the mapping of field and logname to logger
        if (this.logConfig.isEmpty()) {
            this.logConfig.addAll(Arrays.stream(this.getClass().getFields())
                    .filter(DataWrapperBase::fieldTypeIsLoggable).map(LogConfig::fromField)
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
