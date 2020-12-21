package com.godmonth.status.analysis.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p></p >
 *
 * @author shenyue
 */
class AnnotationBeanModelAnalysisTest {
    @Test
    void name() {
        AnnotationBeanModelAnalysis<Object> objectAnnotationBeanModelAnalysis = new AnnotationBeanModelAnalysis<>();
        System.out.println(objectAnnotationBeanModelAnalysis);
    }
}