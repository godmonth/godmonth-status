package com.godmonth.status.executor.impl;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.test.sample.SampleConfigMap;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.tx.impl.AbstractTxStatusTransitor;
import com.godmonth.status.transitor.tx.intf.TransitionCallback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DefaultOrderExecutorTest {

    private static final Logger logger = LoggerFactory.getLogger(DefaultOrderExecutorTest.class);

    private static DefaultOrderExecutor<SampleModel, String, SampleTrigger, SampleStatus> defaultOrderExecutor;

    @BeforeAll
    public static void prepare() {
        BeanModelAnalysis<SampleModel, String, String> analysis = new BeanModelAnalysis<>();
        analysis.setExpectedTypeValue("test");
        analysis.setTypePropertyName("type");
        analysis.setStatusPropertyName("status");
        defaultOrderExecutor = new DefaultOrderExecutor<>();
        defaultOrderExecutor.setModelAnalysis(analysis);
        Map<SampleStatus, StatusAdvancer<SampleModel, String, SampleTrigger>> advancers = new HashMap<>();
        advancers.put(SampleStatus.CREATED, new PayAdvancer());
        advancers.put(SampleStatus.PAID, new CheckAdvancer());
        defaultOrderExecutor.setAdvancerMappings(advancers);
        AbstractTxStatusTransitor<SampleModel, SampleStatus, SampleTrigger> abstractTxStatusTransitor = new AbstractTxStatusTransitor<SampleModel, SampleStatus, SampleTrigger>() {
            @Override
            protected SampleModel mergeModel(SampleModel model, SampleStatus nextStatus,
                                             TransitionCallback<SampleModel> transitionCallback) {
                return model;
            }
        };
        abstractTxStatusTransitor.setTransactionOperations(new TransactionOperations() {

            @Override
            public <T> T execute(TransactionCallback<T> action) throws TransactionException {
                return action.doInTransaction(null);
            }
        });
        SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
                SampleConfigMap.INSTANCE);

        abstractTxStatusTransitor.setStatusTransitor(statusTransitor);
        abstractTxStatusTransitor.setStatusPropertyName("status");
        abstractTxStatusTransitor.setStatusEntryMap(Collections.singletonMap(SampleStatus.PAID, DefaultOrderExecutorTest::print));
        abstractTxStatusTransitor.setStatusExitMap(Collections.singletonMap(SampleStatus.CREATED, DefaultOrderExecutorTest::print));
        defaultOrderExecutor.setTxStatusTransitor(abstractTxStatusTransitor);
    }

    private static void print(SampleModel sampleStatus) {
        logger.debug("sampleStatus:{}", sampleStatus);
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
