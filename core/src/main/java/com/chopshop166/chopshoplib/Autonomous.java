package com.chopshop166.chopshoplib;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface Autonomous {
    // The name of the autonomous.
    String name() default "";

    // Set to true to make the default.
    boolean defaultAuto() default false;
}
