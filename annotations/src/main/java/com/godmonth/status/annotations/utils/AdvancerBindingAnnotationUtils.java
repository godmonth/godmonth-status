package com.godmonth.status.annotations.utils;

import com.godmonth.status.annotations.AdvancerBindingAnnotation;

/**
 * <p></p >
 *
 * @author shenyue
 */

public class AdvancerBindingAnnotationUtils {
    private AdvancerBindingAnnotationUtils() {
    }

    public static Object parseStatusValue(AdvancerBindingAnnotation advancerBindingAnnotation) {
        return AnnotationValueUtils.parseEnumValue(advancerBindingAnnotation, "statusClass", "statusValue");
    }
}
