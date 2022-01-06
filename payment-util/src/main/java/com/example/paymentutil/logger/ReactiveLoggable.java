package com.example.paymentutil.logger;

/*
 * @author Khan Hafizur Rahman
 * @since 5/1/22
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReactiveLoggable {
    boolean enable() default true;
}
