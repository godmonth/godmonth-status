package com.godmonth.status.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>使用modelClass来区分适用的推进器。不支持额外的校验.如果需要额外校验，合成新的标签.</p >
 * <P>如果推进器在xml中声明，或者用@component定义，那么不需要使用Advancer标签.</P>
 *
 * @author shenyue
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Advancer {

    Class modelClass();

}
