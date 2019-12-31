package com.chopshop166.chopshoplib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.wpi.first.wpilibj2.command.Command;

/**
 * The {@link Display} annotation marks a method as returning a {@link Command},
 * and eligible for automatic display on the driver station.
 * <p>
 * This annotation can be used on a method multiple times, with different
 * arguments.
 */
@Repeatable(DisplayItems.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Display {
    /**
     * Parameters that are passed to the annotated method, to provide different
     * resulting commands.
     * <p>
     * Due to Java limitations, the parameters must be of type {@link Double}.
     * 
     * @return An array of parameter values.
     */
    double[] value() default {};

    /**
     * The display name used on the dashboard.
     * <p>
     * By default, the name {@code ""} will expand to the command class's name.
     * 
     * @return The display name of the command.
     */
    String name() default "";
}