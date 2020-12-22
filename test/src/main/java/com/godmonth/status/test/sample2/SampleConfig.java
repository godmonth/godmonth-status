package com.godmonth.status.test.sample2;

import com.godmonth.status.analysis.impl.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.TypeFieldPredicate;
import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.impl.Merger;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.impl.jpa.EntityManagerMergerImpl;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import java.util.Arrays;

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
        return EntityManagerMergerImpl.builder().entityManager(entityManager).build();
    }

    public TxStatusTransitor txStatusTransitor(Merger merger, TransactionOperations transactionOperations, StatusTransitor statusTransitor, ModelAnalysis<SampleModel> modelAnalysis) {
        return TxStatusTransitorImpl.builder().transactionOperations(transactionOperations).statusTransitor(statusTransitor).modelAnalysis(modelAnalysis).build();
    }


}
