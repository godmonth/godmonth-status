package com.godmonth.status.executor.impl;

import com.godmonth.status.executor.impl.analysis.AnnotationBeanModelAnalysis;
import com.godmonth.status.test.sample.SampleModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p></p >
 *
 * @author shenyue
 */
class AnnotationBeanModelAnalysisTest {
    @Test
    void construct() {
        AnnotationBeanModelAnalysis<SampleModel> build = AnnotationBeanModelAnalysis.<SampleModel>builder().modelClass(SampleModel.class).build();
        Assertions.assertNotNull(build);
    }
}