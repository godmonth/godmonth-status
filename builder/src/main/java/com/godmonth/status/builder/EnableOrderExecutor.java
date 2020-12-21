package com.godmonth.status.builder;

import com.godmonth.status.builder.statemachine.definition.intf.StatusDefinition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p >
 *
 * @author shenyue
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableOrderExecutor {
    //String[] basePackages() default {};

    Class<? extends StatusDefinition> aaa();
}
