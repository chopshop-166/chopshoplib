package com.chopshop166.chopshoplib.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to mark that this class is a data logger for the given class. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoggerDataFor {
    /**
     * The class to log data for.
     * 
     * @return The class object.
     **/
    Class<?> value();
}
