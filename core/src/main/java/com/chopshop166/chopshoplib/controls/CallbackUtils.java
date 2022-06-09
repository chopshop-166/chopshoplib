package com.chopshop166.chopshoplib.controls;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.util.function.BooleanConsumer;

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
     * Register a callback for a key on a network table.
     * 
     * @param tableName The table to check in.
     * @param key       The key to trigger the callback.
     * @param cons      The callback.
     */
    public static void register(final String tableName, final String key, final BooleanConsumer cons) {
        NetworkTableInstance.getDefault().getTable(tableName).addEntryListener(key, configureFromNT(cons), ON_UPDATE);
    }

    /**
     * Register a callback for a key on a network table.
     * 
     * @param tableName The table to check in.
     * @param key       The key to trigger the callback.
     * @param cons      The callback.
     */
    public static void register(final String tableName, final String key, final Consumer<String> cons) {
        NetworkTableInstance.getDefault().getTable(tableName).addEntryListener(key, configureFromNT(cons), ON_UPDATE);
    }

    /**
     * Callback that calls a {@link DoubleConsumer} whenever a key updates.
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

    /**
     * Callback that calls a {@link BooleanConsumer} whenever a key updates.
     * 
     * @param cons The consumer to call with the new value.
     * @return A function object.
     */
    public static TableEntryListener configureFromNT(final BooleanConsumer cons) {
        return (NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags) -> {
            if (value.isDouble()) {
                cons.accept(value.getBoolean());
            }
        };
    }

    /**
     * Callback that calls a {@link Consumer} whenever a key updates.
     * 
     * @param cons The consumer to call with the new value.
     * @return A function object.
     */
    public static TableEntryListener configureFromNT(final Consumer<String> cons) {
        return (NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags) -> {
            if (value.isString()) {
                cons.accept(value.getString());
            }
        };
    }
}
