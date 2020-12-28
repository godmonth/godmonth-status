package com.godmonth.status.test.sample.machine.cfg2;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.analysis.impl.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.TypeFieldPredicate;
import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.builder.advancer.AdvancerFunctionBuilder;
import com.godmonth.status.builder.entry.EntryFunctionBuilder;
import com.godmonth.status.builder.transitor.JsonDefinitionBuilder;
import com.godmonth.status.executor.impl.DefaultOrderExecutor;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.impl.Merger;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

/**
 * <p></p >
 *
 * @author shenyue
 */
//@ComponentScan
//@Configuration
public class SampleOrderExecutorConfig {


    //多个执行器可以公用
    @Bean
    public Merger merger(EntityManager entityManager) {
        return entityManager::merge;
    }

    @Bean
    public ModelAnalysis<SampleModel> sampleModelModelAnalysis() {
        return AnnotationBeanModelAnalysis.<SampleModel>builder().modelClass(SampleModel.class).predicateList(Arrays.asList(TypeFieldPredicate.builder().propertyName("type").expectedValue("test").build())).build();
    }

    @Bean
    public StatusTransitor<SampleStatus, SampleTrigger> sampleStatusStatusTransitor(@Value("classpath:/sample-status.json") Resource configResource) throws IOException {
        Function<SampleStatus, Function<SampleTrigger, SampleStatus>> function = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().resource(configResource).statusClass(SampleStatus.class).triggerClass(SampleTrigger.class).build();
        return new SimpleStatusTransitor(function);
    }

    @Bean
    public TxStatusTransitor sampleStatusTxStatusTransitor(Merger merger, TransactionOperations transactionOperations, @Qualifier("sampleStatusStatusTransitor") StatusTransitor statusTransitor, @Qualifier("sampleModelModelAnalysis") ModelAnalysis<SampleModel> modelAnalysis, @Qualifier("sampleStatusStatusEntryFunction") Function<SampleStatus, StatusEntry> entryFunction) {
        return TxStatusTransitorImpl.builder().transactionOperations(transactionOperations).modelMerger(merger).statusTransitor(statusTransitor).modelAnalysis(modelAnalysis).statusEntryFunction(entryFunction).build();
    }

    /**
     * 推进器可以根据需求灵活的定义
     *
     * @param beanFactory
     * @param modelAnalysis
     * @param txStatusTransitor
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Bean
    public OrderExecutor<SampleModel, String> sampleModelOrderExecutor(AutowireCapableBeanFactory beanFactory, @Qualifier("sampleModelModelAnalysis") ModelAnalysis<SampleModel> modelAnalysis, @Qualifier("sampleStatusTxStatusTransitor") TxStatusTransitor txStatusTransitor) throws IOException, ClassNotFoundException {
        Function<Object, StatusAdvancer> function = AdvancerFunctionBuilder.builder().autowireCapableBeanFactory(beanFactory).modelClass(SampleModel.class).packageName("com.godmonth.status.test.sample2.advancer").build();
        return DefaultOrderExecutor.<SampleModel, Void, Object>builder().modelAnalysis(modelAnalysis).advancerFunctions(function).
                txStatusTransitor(txStatusTransitor).build();
    }

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
        Function<SampleStatus, StatusEntry> entryFunction = EntryFunctionBuilder.<SampleStatus>builder().autowireCapableBeanFactory(beanFactory).packageName("com.godmonth.status.test.sample2.entry").statusClass(SampleStatus.class).build();
        return entryFunction;
    }
}
