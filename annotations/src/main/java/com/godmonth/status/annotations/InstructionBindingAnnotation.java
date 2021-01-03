package com.godmonth.status.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InstructionBindingAnnotation {
    /**
     * 只能是string或者枚举
     *
     * @return
     */
    Class instructionClass();

    String instructionValue();
}
