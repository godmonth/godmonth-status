package com.godmonth.status.test.sample2.advancer;

import com.godmonth.status.annotations.Advancer;
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
        Advancer annotation = AnnotationUtils.findAnnotation(PayAdvancer.class, Advancer.class);
        System.out.println(annotation);
    }
}