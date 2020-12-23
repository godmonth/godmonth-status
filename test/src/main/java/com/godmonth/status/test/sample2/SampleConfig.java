package com.godmonth.status.test.sample2;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.analysis.impl.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.TypeFieldPredicate;
import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.annotations.Advancer;
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
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ComponentScan
@Configuration
public class SampleConfig {
    @Bean
    public ModelAnalysis<SampleModel> sampleModelModelAnalysis() {
        return AnnotationBeanModelAnalysis.<SampleModel>builder().modelClass(SampleModel.class).predicateList(Arrays.asList(TypeFieldPredicate.builder().propertyName("type").expectedValue("test").build())).build();
    }

    @Bean
    public Merger merger(EntityManager entityManager) {
        return entityManager::merge;
    }

    @Bean
    public StatusTransitor<SampleStatus, SampleTrigger> sampleStatusStatusTransitor(@Value("classpath:/sample-status.json") Resource configResource) throws IOException {
        Map<SampleStatus, Map<SampleTrigger, SampleStatus>> map = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().resource(configResource).statusClass(SampleStatus.class).triggerClass(SampleTrigger.class).build();
        return new SimpleStatusTransitor(map);
    }

    @Bean
    public TxStatusTransitor sampleStatusTxStatusTransitor(Merger merger, TransactionOperations transactionOperations, @Qualifier("sampleStatusStatusTransitor") StatusTransitor statusTransitor, @Qualifier("sampleModelModelAnalysis") ModelAnalysis<SampleModel> modelAnalysis, Optional<Map> statusEntryMap) {
        return TxStatusTransitorImpl.builder().transactionOperations(transactionOperations).modelMerger(merger).statusTransitor(statusTransitor).modelAnalysis(modelAnalysis).statusEntryMap(statusEntryMap.orElse(null)).build();
    }

    @Bean
    public OrderExecutor<SampleModel, Void> sampleModelOrderExecutor(ObjectProvider<StatusAdvancer> objectProvider, @Qualifier("sampleModelModelAnalysis") ModelAnalysis<SampleModel> modelAnalysis, @Qualifier("sampleStatusTxStatusTransitor") TxStatusTransitor txStatusTransitor) {
        for (StatusAdvancer statusAdvancer : objectProvider) {
            System.out.println(statusAdvancer);
        }
        List<StatusAdvancer> statusAdvancers = objectProvider.stream().filter(new Predicate<StatusAdvancer>() {
            @Override
            public boolean test(StatusAdvancer statusAdvancer) {
                Advancer annotation = AnnotationUtils.getAnnotation(statusAdvancer.getClass(), Advancer.class);
//                return SampleModel.class.equals(annotation.modelClass());
                return SampleModel.class.equals(annotation.modelClass()) && "test".equals(annotation.typeFieldValue());
            }
        }).collect(Collectors.toList());
        return DefaultOrderExecutor.<SampleModel, Void, Object>builder().modelAnalysis(modelAnalysis).txStatusTransitor(txStatusTransitor).build();
    }

}
