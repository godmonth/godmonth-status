package com.godmonth.status.test.sample.executor;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.analysis.impl.model.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.model.TypeFieldPredicate;
import com.godmonth.status.executor.impl.DefaultOrderExecutor;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.advancer.CheckAdvancer;
import com.godmonth.status.test.sample.machine.advancer.PayAdvancer;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TransitedResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DefaultOrderExecutorTest2 {

    private static final Logger logger = LoggerFactory.getLogger(DefaultOrderExecutorTest2.class);

    private static DefaultOrderExecutor<SampleModel, String, SampleTrigger> defaultOrderExecutor;

    @BeforeAll
    public static void prepare() {
        defaultOrderExecutor = new DefaultOrderExecutor<>();
        TypeFieldPredicate typeFieldPredicate = TypeFieldPredicate.builder().propertyName("type").expectedValue("test").build();
        AnnotationBeanModelAnalysis analysis = AnnotationBeanModelAnalysis.<SampleModel>annoBuilder().modelClass(SampleModel.class).predicateList(Arrays.asList(typeFieldPredicate)).build();
        defaultOrderExecutor.setModelAnalysis(analysis);
        Map<SampleStatus, StatusAdvancer<SampleModel, String, SampleTrigger>> advancers = new HashMap<>();
        advancers.put(SampleStatus.CREATED, new PayAdvancer());
        advancers.put(SampleStatus.PAID, new CheckAdvancer());
        defaultOrderExecutor.setAdvancerFunctions(advancers::get);
        TxStatusTransitorImpl<SampleModel, SampleStatus, SampleTrigger> txStatusTransitor = new TxStatusTransitorImpl<>();


        SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
                SampleConfigMap.INSTANCE);

        txStatusTransitor.setStatusTransitor(statusTransitor);
        txStatusTransitor.setModelAnalysis(analysis);
        Map<SampleStatus, StatusEntry> sampleStatusStatusEntryMap = Collections.singletonMap(SampleStatus.PAID, DefaultOrderExecutorTest2::print);
        txStatusTransitor.setStatusEntryFunction(sampleStatusStatusEntryMap::get);
        txStatusTransitor.setModelMerger(sampleModel -> sampleModel);
        txStatusTransitor.setTransactionOperations(TransactionOperations.withoutTransaction());
        defaultOrderExecutor.setTxStatusTransitor(txStatusTransitor);
    }

    private static void print(TransitedResult<SampleModel, ?> transitedResult) {
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
