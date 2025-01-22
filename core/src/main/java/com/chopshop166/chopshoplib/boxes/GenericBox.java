package com.chopshop166.chopshoplib.boxes;

import java.util.function.Consumer;
import com.google.common.base.Supplier;

/**
 * Box to wrap a value in a final object.
 * 
 * @param <T> The boxed type.
 */
public class GenericBox<T> implements Supplier<T>, Consumer<T> {
    /** The boxed object. */
    private T data;

    /** Constructor. */
    public GenericBox() {
        // This line intentionally left blank
    }

    /**
     * Constructor.
     * 
     * @param value The starting value for the box.
     */
    public GenericBox(final T value) {
        this.data = value;
    }

    @Override
    public void accept(final T value) {
        this.data = value;
    }

    @Override
    public T get() {
        return this.data;
    }
}
