package com.godmonth.status.analysis.impl;

import org.junit.jupiter.api.Test;


/**
 * <p></p >
 *
 * @author shenyue
 */
public class AnnotationBeanModelAnalysisTest {
    @Test
    void name() {
        AnnotationBeanModelAnalysis<Object> objectAnnotationBeanModelAnalysis = new AnnotationBeanModelAnalysis<>();
        System.out.println(objectAnnotationBeanModelAnalysis);
    }
}