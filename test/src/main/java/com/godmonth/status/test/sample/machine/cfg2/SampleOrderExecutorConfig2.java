package com.godmonth.status.test.sample.machine.cfg2;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.analysis.impl.model.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.model.TypeFieldPredicate;
import com.godmonth.status.analysis.impl.sm.AnnotationStateMachineAnalysis;
import com.godmonth.status.analysis.intf.StateMachineAnalysis;
import com.godmonth.status.builder.advancer.AdvancerListBuilder;
import com.godmonth.status.builder.entry.StatusEntryBindingListBuilder;
import com.godmonth.status.builder.transitor.JsonDefinitionBuilder;
import com.godmonth.status.executor.impl.DefaultOrderExecutor;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.impl.Merger;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.StatusEntryBinding;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ComponentScan
@Configuration
public class SampleOrderExecutorConfig2 {

    //多个执行器可以公用
    @Bean
    public Merger merger(EntityManager entityManager) {
        return entityManager::merge;
    }

    @Bean
    public StateMachineAnalysis sampleStateMachineAnalysis() {
        AnnotationBeanModelAnalysis modelAnalysis = AnnotationBeanModelAnalysis.<SampleModel>annoBuilder().modelClass(SampleModel.class).predicateList(Arrays.asList(TypeFieldPredicate.builder().propertyName("type").expectedValue("test").build())).build();
        return AnnotationStateMachineAnalysis.annoBuilder().modelAnalysis(modelAnalysis).build();
    }


    /**
     * 推进器可以根据需求灵活的定义
     *
     * @param beanFactory
     * @param txStatusTransitor
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Bean
    public OrderExecutor<SampleModel, String> sampleModelOrderExecutor(AutowireCapableBeanFactory beanFactory, @Qualifier("sampleStateMachineAnalysis") StateMachineAnalysis sampleStateMachineAnalysis, @Qualifier("sampleStatusTxStatusTransitor") TxStatusTransitor txStatusTransitor) throws IOException, ClassNotFoundException {
        List<StatusAdvancer> advancerList = AdvancerListBuilder.builder().autowireCapableBeanFactory(beanFactory).modelClass(SampleModel.class).packageName("com.godmonth.status.test.sample.machine.advancer2").build();
        return DefaultOrderExecutor.<SampleModel, Void, Object>builder().modelAnalysis(sampleStateMachineAnalysis.getModelAnalysis()).statusAdvancerList(advancerList).txStatusTransitor(txStatusTransitor).build();
    }

    @Bean
    public TxStatusTransitor sampleStatusTxStatusTransitor(Merger merger, TransactionOperations transactionOperations, @Qualifier("sampleStatusTransitor") StatusTransitor statusTransitor, @Qualifier("sampleStateMachineAnalysis") StateMachineAnalysis sampleStateMachineAnalysis, AutowireCapableBeanFactory beanFactory) throws IOException, ClassNotFoundException {
        List<StatusEntryBinding> bindingList = StatusEntryBindingListBuilder.<SampleStatus>builder().autowireCapableBeanFactory(beanFactory).packageName("com.godmonth.status.test.sample.machine.entry2").statusClass(SampleStatus.class).build();
        return TxStatusTransitorImpl.builder().modelMerger(merger).modelAnalysis(sampleStateMachineAnalysis.getModelAnalysis()).statusTransitor(statusTransitor).statusEntryBindList(bindingList).build();
    }

    @Bean
    public StatusTransitor<SampleStatus, SampleTrigger> sampleStatusTransitor(@Value("classpath:/sample-status.json") Resource configResource, @Qualifier("sampleStateMachineAnalysis") StateMachineAnalysis sampleStateMachineAnalysis) throws IOException {
        Function<SampleStatus, Function<SampleTrigger, SampleStatus>> function = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().resource(configResource).stateMachineAnalysis(sampleStateMachineAnalysis).build();
        return new SimpleStatusTransitor(function);
    }

}
