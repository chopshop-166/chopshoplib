package com.chopshop166.chopshoplib.controls;

import java.util.function.DoubleConsumer;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;

/** Collection of callbacks for things like Network Tables. */
public final class CallbackUtils {

    /** Bitmask to handle updates. */
    public static final int ON_UPDATE = EntryListenerFlags.kImmediate | EntryListenerFlags.kUpdate;

    private CallbackUtils() {
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
