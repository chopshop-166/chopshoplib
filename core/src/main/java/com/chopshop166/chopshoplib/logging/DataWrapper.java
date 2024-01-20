package com.chopshop166.chopshoplib.logging;

import org.littletonrobotics.junction.LogTable;

/** Data wrapper that works on a subtable. */
public class DataWrapper extends DataWrapperBase {
    /** The name of the subtable. */
    public final String name;

    /**
     * Constructor.
     * 
     * @param name The name of the subtable to use.
     */
    public DataWrapper(final String name) {
        super();
        this.name = name;
    }

    @Override
    protected LogTable getTableFrom(final LogTable table) {
        return table.getSubtable(this.name);
    }
}
