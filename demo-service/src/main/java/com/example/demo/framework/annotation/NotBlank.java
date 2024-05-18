package com.example.demo.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JiakunXu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotBlank {
    /**
     * @return Custom exception message
     */
    String value() default "";

    /**
     * @return Custom exception type that should be thrown when not-nullity contract is violated.
     * The exception class should have a constructor with one String argument (message).
     *
     * By default, {@link IllegalArgumentException} is thrown on null method arguments and
     * {@link IllegalStateException} &mdash; on null return value.
     */
    Class<? extends Exception> exception() default Exception.class;
}
