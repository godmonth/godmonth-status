package com.godmonth.status.transitor.bean.impl;

import com.godmonth.status.test.sample.SampleConfigMap;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class BeanStatusTransitorImplTest {

    private static final Logger logger = LoggerFactory.getLogger(BeanStatusTransitorImplTest.class);

    private static BeanStatusTransitorImpl<SampleModel, SampleStatus, SampleTrigger> beanStatusTransitor = new BeanStatusTransitorImpl<>();

    @BeforeAll
    public static void prepare() throws IOException {
        SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
                SampleConfigMap.INSTANCE);
        beanStatusTransitor.setStatusTransitor(statusTransitor);
        beanStatusTransitor.setStatusPropertyName("status");
        beanStatusTransitor.setStatusEntryMap(Collections.singletonMap(SampleStatus.PAID, BeanStatusTransitorImplTest::printEntry));
        beanStatusTransitor.setStatusExitMap(Collections.singletonMap(SampleStatus.CREATED, BeanStatusTransitorImplTest::printExit));
    }

    private static void printExit(SampleModel sampleStatus) {
        logger.debug("sampleStatus:{}", sampleStatus);
    }

    private static void printEntry(SampleModel sampleStatus) {
        logger.debug("sampleStatus:{}", sampleStatus);
    }

    @Test
    public void transit() {
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        SampleModel transit = beanStatusTransitor.transit(sampleModel, SampleTrigger.PAY);
        Assertions.assertEquals(transit.getStatus(), SampleStatus.PAID);
        System.out.println(transit);
    }
}
