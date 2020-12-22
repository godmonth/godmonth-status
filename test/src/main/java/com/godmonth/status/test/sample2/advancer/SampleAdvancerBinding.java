package com.godmonth.status.test.sample2.advancer;

import com.godmonth.status.annotations.Advancer;
import com.godmonth.status.test.sample.domain.SampleModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p></p >
 *
 * @author shenyue
 */
@Advancer(modelClass = SampleModel.class, typeFieldName = "type", typeFieldValue = "test")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SampleAdvancerBinding {
}
