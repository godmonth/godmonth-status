package com.godmonth.status.analysis.impl;

import com.godmonth.status.analysis.intf.ModelAnalysis;
import org.apache.commons.lang3.AnnotationUtils;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class AnnotationMachineAnalysis {
    private ModelAnalysis modelAnalysis;

    public AnnotationMachineAnalysis(ModelAnalysis modelAnalysis) {
        Class statusClass = modelAnalysis.getStatusClass();
    }
}
