package com.godmonth.status.test.sample2;

import com.godmonth.status.analysis.impl.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.TypeFieldPredicate;
import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.builder.transitor.JsonDefinitionBuilder;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.impl.Merger;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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
    public <SampleModel, SampleStatus, SampleTrigger> TxStatusTransitor sampleStatusTxStatusTransitor(Merger merger, TransactionOperations transactionOperations, @Qualifier("sampleStatusStatusTransitor") StatusTransitor statusTransitor, @Qualifier("sampleModelModelAnalysis") ModelAnalysis<SampleModel> modelAnalysis, Optional<Map<SampleStatus, StatusEntry<SampleModel, SampleTrigger>>> statusEntryMap) {
        return TxStatusTransitorImpl.builder().transactionOperations(transactionOperations).modelMerger(merger).statusTransitor(statusTransitor).modelAnalysis(modelAnalysis).statusEntryMap(statusEntryMap.orElse(null)).build();
    }


}
