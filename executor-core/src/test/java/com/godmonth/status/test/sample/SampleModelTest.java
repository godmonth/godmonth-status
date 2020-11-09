package com.godmonth.status.test.sample;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * <p></p >
 *
 * @author shenyue
 */
class SampleModelTest {
    @Test
    void name() {

        Field[] allFields = FieldUtils.getAllFields(SampleModel.class);
        System.out.println(ArrayUtils.toString(allFields));
    }
}