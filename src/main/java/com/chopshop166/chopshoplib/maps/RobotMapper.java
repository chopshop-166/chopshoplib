package com.chopshop166.chopshoplib.maps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.chopshop166.chopshoplib.RobotUtils;

/** Class to make a list of robot map creators. */
public class RobotMapper<T> {

    /** A mapping of suppliers to use. */
    private final Map<String, Supplier<T>> suppliers = new HashMap<>();

    /**
     * Add an option.
     * 
     * @param name     The name to use for selection.
     * @param supplier The function to get a map from.
     * @return This object.
     */
    public RobotMapper<T> maybe(final String name, final Supplier<T> supplier) {
        suppliers.put(name, supplier);
        return this;
    }

    /**
     * Get the appropriate map via MAC address.
     * 
     * @return A robot map, or null if none match.
     */
    public T get() {
        return get(RobotUtils.getMACAddress(), null);
    }

    /**
     * Get the appropriate map via name.
     * 
     * @param name The name to look up.
     * @return A robot map, or null if none match.
     */
    public T get(final String name) {
        return get(name, null);
    }

    /**
     * Get the appropriate map via MAC address.
     * 
     * @param defaultValue The default value.
     * @return A robot map, or the default value if none match.
     */
    public T get(final T defaultValue) {
        return get(RobotUtils.getMACAddress(), defaultValue);
    }

    /**
     * Get the appropriate map via name.
     * 
     * @param name         The name to look up.
     * @param defaultValue The default value.
     * @return A robot map, or the default value if none match.
     */
    public T get(final String name, final T defaultValue) {
        return suppliers.getOrDefault(name, () -> defaultValue).get();
    }
}
