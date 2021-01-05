package com.godmonth.status.test.sample.machine.advancer2;

import com.godmonth.status.annotations.binding.StatusBinding;
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
        StatusBinding annotation = AnnotationUtils.findAnnotation(PayAdvancer2.class, StatusBinding.class);
        System.out.println(annotation);
    }
}