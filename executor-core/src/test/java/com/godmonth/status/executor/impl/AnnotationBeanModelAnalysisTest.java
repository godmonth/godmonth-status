package com.godmonth.status.executor.impl;

import com.godmonth.status.executor.impl.analysis.AnnotationBeanModelAnalysis;
import com.godmonth.status.test.sample.SampleModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * <p></p >
 *
 * @author shenyue
 */
class AnnotationBeanModelAnalysisTest {
    @Test
    void construct() {
        AnnotationBeanModelAnalysis<SampleModel> build = AnnotationBeanModelAnalysis.<SampleModel>builder().modelClass(SampleModel.class).beanModelRestrictions(new ArrayList<>()).build();
        Assertions.assertNotNull(build);
    }
}