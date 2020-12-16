package com.godmonth.status.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>标记status字段</p >
 *
 * @author shenyue
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Status {
}