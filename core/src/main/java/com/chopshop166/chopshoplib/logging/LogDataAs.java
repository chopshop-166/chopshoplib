package com.chopshop166.chopshoplib.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Marker to tell the data object generator to use this data object. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogDataAs {
    /**
     * The class to use.
     * 
     * @return The class type.
     **/
    Class<?> value();
}
