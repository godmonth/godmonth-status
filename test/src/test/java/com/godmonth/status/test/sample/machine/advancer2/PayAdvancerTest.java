package com.godmonth.status.test.sample.machine.advancer2;

import com.godmonth.status.annotations.AdvancerBindingAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * <p></p >
 *
 * @author shenyue
 */
class PayAdvancerTest {
    @Test
    void name() {
        AdvancerBindingAnnotation annotation = AnnotationUtils.findAnnotation(PayAdvancer.class, AdvancerBindingAnnotation.class);
        System.out.println(annotation);
    }
}