package com.chopshop166.chopshoplib.controls;

import java.util.function.DoubleConsumer;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;

/** Collection of callbacks for things like Network Tables. */
public final class CallbackUtils {

    /** Bitmask to handle updates. */
    public static final int ON_UPDATE = EntryListenerFlags.kImmediate | EntryListenerFlags.kUpdate;

    private CallbackUtils() {
    }

    /**
     * Register a callback for a key on a network table.
     * 
     * @param tableName The table to check in.
     * @param key       The key to trigger the callback.
     * @param cons      The callback.
     */
    public static void register(final String tableName, final String key, final DoubleConsumer cons) {
        NetworkTableInstance.getDefault().getTable(tableName).addEntryListener(key, configureFromNT(cons), ON_UPDATE);
    }

    /**
     * Callback that calls a DoubleConsumer whenever a key updates.
     * 
     * @param cons The consumer to call with the new value.
     * @return A function object.
     */
    public static TableEntryListener configureFromNT(final DoubleConsumer cons) {
        return (NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags) -> {
            if (value.isDouble()) {
                cons.accept(value.getDouble());
            }
        };
    }
}
