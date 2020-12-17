package com.godmonth.status.executor.impl;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.executor.impl.analysis.SimpleBeanModelAnalysis;
import com.godmonth.status.executor.impl.analysis.TypeFieldPredicate;
import com.godmonth.status.test.sample.SampleConfigMap;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.TransitedResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DefaultOrderExecutorTest {

    private static final Logger logger = LoggerFactory.getLogger(DefaultOrderExecutorTest.class);

    private static DefaultOrderExecutor<SampleModel, String, SampleTrigger> defaultOrderExecutor;

    @BeforeAll
    public static void prepare() {
        SimpleBeanModelAnalysis<SampleModel> analysis = new SimpleBeanModelAnalysis<>();
        analysis.setStatusPropertyName("status");
//        analysis.setPredicateList(Arrays.asList(TypeFieldPredicate.builder().expectedTypeValue("")));
        defaultOrderExecutor = new DefaultOrderExecutor<>();
        defaultOrderExecutor.setModelAnalysis(analysis);
        Map<SampleStatus, StatusAdvancer<SampleModel, String, SampleTrigger>> advancers = new HashMap<>();
        advancers.put(SampleStatus.CREATED, new PayAdvancer());
        advancers.put(SampleStatus.PAID, new CheckAdvancer());
        defaultOrderExecutor.setAdvancerMappings(advancers);

        TxStatusTransitorImpl<SampleModel, SampleStatus, SampleTrigger> txStatusTransitor = new TxStatusTransitorImpl<>();
        SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
                SampleConfigMap.INSTANCE);

        txStatusTransitor.setStatusTransitor(statusTransitor);
        txStatusTransitor.setStatusPropertyName("status");
        txStatusTransitor.setStatusEntryMap(Collections.singletonMap(SampleStatus.PAID, DefaultOrderExecutorTest::print));
        txStatusTransitor.setModelMerger(sampleModel -> sampleModel);
        defaultOrderExecutor.setTxStatusTransitor(txStatusTransitor);
    }

    private static void print(TransitedResult transitedResult) {
        logger.debug("transitedResult:{}", transitedResult);
    }

    @Test
    public void execute() {
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        sampleModel.setType("test");
        SyncResult<SampleModel, ?> execute = defaultOrderExecutor.execute(sampleModel, "eee", "fff");
        Assertions.assertEquals(execute.getModel().getStatus(), SampleStatus.PAID);
    }

    @Test
    public void execute2() {
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        sampleModel.setType("test");
        SyncResult<SampleModel, ?> execute = defaultOrderExecutor.execute(sampleModel, null, null);
        Assertions.assertEquals(execute.getModel().getStatus(), SampleStatus.CREATED);
    }
}
