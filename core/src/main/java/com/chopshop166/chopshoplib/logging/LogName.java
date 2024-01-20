package com.chopshop166.chopshoplib.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to change the name used for logging. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogName {
    /**
     * The name to log as.
     * 
     * @return The name.
     **/
    String value();
}
