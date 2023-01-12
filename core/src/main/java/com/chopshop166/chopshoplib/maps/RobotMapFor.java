package com.chopshop166.chopshoplib.maps;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as being a RobotMap for a given type.
 */
@Repeatable(RobotMapForCollection.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RobotMapFor {
    /**
     * Get the ID of the robot to use.
     * 
     * @return The ID of the robot to use.
     */
    String value();
}
