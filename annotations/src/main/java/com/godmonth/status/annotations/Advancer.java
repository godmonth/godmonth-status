package com.godmonth.status.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>使用modelClass来区分适用的推进器。不支持额外的校验</p >
 *
 * @author shenyue
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Advancer {

    Class modelClass();

    /**
     * 限制子类型名字
     *
     * @return
     */
    String typeFieldName() default "";

    /**
     * 限制子类型值.值
     *
     * @return
     */
    String typeFieldValue() default "";
}
