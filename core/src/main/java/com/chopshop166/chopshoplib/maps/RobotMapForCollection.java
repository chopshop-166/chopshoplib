package com.chopshop166.chopshoplib.maps;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Collection of {@link RobotMapFor} instances.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RobotMapForCollection {
    /**
     * All the robots this is a map for.
     * 
     * @return A list of robot identities.
     */
    RobotMapFor[] value();
}