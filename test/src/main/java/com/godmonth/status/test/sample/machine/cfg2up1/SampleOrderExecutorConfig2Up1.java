package com.godmonth.status.test.sample.machine.cfg2up1;

import com.godmonth.status.analysis.impl.model.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.model.TypeFieldPredicate;
import com.godmonth.status.analysis.impl.sm.AnnotationStateMachineAnalysis;
import com.godmonth.status.analysis.intf.StateMachineAnalysis;
import com.godmonth.status.builder.entry.StatusEntryBindingListBuilder;
import com.godmonth.status.builder.transitor.JsonDefinitionBuilder;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.tx.impl.Merger;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionOperations;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ComponentScan
@Configuration
public class SampleOrderExecutorConfig2Up1 {


    @Bean
    public AnnotationStateMachineAnalysis sampleStateMachineAnalysis() {
        AnnotationBeanModelAnalysis analysis = AnnotationBeanModelAnalysis.<SampleModel>annoBuilder().modelClass(SampleModel.class).predicateList(Arrays.asList(TypeFieldPredicate.builder().propertyName("type").expectedValue("test").build())).build();
        return AnnotationStateMachineAnalysis.annoBuilder().modelAnalysis(analysis).build();
    }

    public TxStatusTransitor sampleStatusTxStatusTransitor(Resource configResource, StateMachineAnalysis machineAnalysis, Merger merger, TransactionOperations transactionOperations, Function<SampleStatus, StatusEntry> entryFunction) throws IOException {
        Function<SampleStatus, Function<SampleTrigger, SampleStatus>> function = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().resource(configResource).stateMachineAnalysis(machineAnalysis).build();
        SimpleStatusTransitor simpleStatusTransitor = new SimpleStatusTransitor(function);

        return TxStatusTransitorImpl.builder().transactionOperations(transactionOperations).modelMerger(merger).statusTransitor(simpleStatusTransitor).modelAnalysis(machineAnalysis.getModelAnalysis()).statusEntryFunction(entryFunction).build();
    }

    /**
     * 推进器可以根据需求灵活的定义
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
//    @Bean
//    public OrderExecutor<SampleModel, String> sampleModelOrderExecutor(StateMachineAnalysis machineAnalysis, Function<Object, StatusAdvancer> function) throws IOException, ClassNotFoundException {
//        Function<Object, StatusAdvancer> function = AdvancerFunctionBuilder.builder().autowireCapableBeanFactory(beanFactory).modelClass(SampleModel.class).packageName("com.godmonth.status.test.sample.machine.advancer2").build();
//        return DefaultOrderExecutor.<SampleModel, Void, Object>builder().modelAnalysis(modelAnalysis).advancerFunctions(function).
//                txStatusTransitor(txStatusTransitor).build();
//    }

    /**
     * entry可以按照需求灵活的定义
     *
     * @param beanFactory
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Bean
    public Function<SampleStatus, StatusEntry> sampleStatusStatusEntryFunction(AutowireCapableBeanFactory beanFactory) throws IOException, ClassNotFoundException {
        Function<SampleStatus, StatusEntry> entryFunction = StatusEntryBindingListBuilder.<SampleStatus>builder().autowireCapableBeanFactory(beanFactory).packageName("com.godmonth.status.test.sample.machine.entry2").statusClass(SampleStatus.class).build();
        return entryFunction;
    }
}
