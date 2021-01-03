package com.godmonth.status.annotations.utils;

import com.godmonth.status.annotations.InstructionBindingAnnotation;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class InstructionBindingAnnotationUtils {
    private InstructionBindingAnnotationUtils() {
    }

    public static Object parseInstructionValue(InstructionBindingAnnotation annotation) {
        return AnnotationValueUtils.parseEnumValue(annotation, "instructionClass", "instructionValue");
    }
}
