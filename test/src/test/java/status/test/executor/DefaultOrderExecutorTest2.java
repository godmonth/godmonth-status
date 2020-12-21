package status.test.executor;

import status.advancer.intf.StatusAdvancer;
import status.advancer.intf.SyncResult;
import status.analysis.impl.AnnotationBeanModelAnalysis;
import status.analysis.impl.TypeFieldPredicate;
import status.executor.impl.DefaultOrderExecutor;
import status.test.sample.domain.SampleModel;
import status.test.sample.domain.SampleStatus;
import status.test.sample.machine.advancer.CheckAdvancer;
import status.test.sample.machine.advancer.PayAdvancer;
import status.test.sample.machine.trigger.SampleTrigger;
import status.transitor.core.impl.SimpleStatusTransitor;
import status.transitor.tx.impl.TxStatusTransitorImpl;
import status.transitor.tx.intf.TransitedResult;
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
        AnnotationBeanModelAnalysis analysis = AnnotationBeanModelAnalysis.<SampleModel>builder().modelClass(SampleModel.class).predicateList(Arrays.asList(typeFieldPredicate)).build();
        defaultOrderExecutor.setModelAnalysis(analysis);
        Map<SampleStatus, StatusAdvancer<SampleModel, String, SampleTrigger>> advancers = new HashMap<>();
        advancers.put(SampleStatus.CREATED, new PayAdvancer());
        advancers.put(SampleStatus.PAID, new CheckAdvancer());
        defaultOrderExecutor.setAdvancerMappings(advancers);
        TxStatusTransitorImpl<SampleModel, SampleStatus, SampleTrigger> txStatusTransitor = new TxStatusTransitorImpl<>();


        SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
                SampleConfigMap.INSTANCE);

        txStatusTransitor.setStatusTransitor(statusTransitor);
        txStatusTransitor.setModelAnalysis(analysis);
        txStatusTransitor.setStatusEntryMap(Collections.singletonMap(SampleStatus.PAID, DefaultOrderExecutorTest2::print));
        txStatusTransitor.setModelMerger(sampleModel -> sampleModel);
        txStatusTransitor.setTransactionOperations(TransactionOperations.withoutTransaction());
        defaultOrderExecutor.setTxStatusTransitor(txStatusTransitor);
    }

    private static void print(TransitedResult<SampleModel, ?> sampleStatus) {
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
