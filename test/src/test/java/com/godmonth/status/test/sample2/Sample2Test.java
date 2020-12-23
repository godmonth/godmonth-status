package com.godmonth.status.test.sample2;

import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.repo.RepoConfig;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ComponentScan
@AutoConfigureDataJpa
@SpringBootTest(classes = {RepoConfig.class, SampleConfig.class})
@EnableAutoConfiguration
public class Sample2Test {
    @Autowired
    private ModelAnalysis<SampleModel> sampleModelModelAnalysis;

    @Autowired
    private TxStatusTransitor sampleStatusTxStatusTransitor;

    @Autowired
    private OrderExecutor<SampleModel, Void> sampleModelOrderExecutor;

    @Test
    void name() {
        System.out.println(sampleModelOrderExecutor);
    }
}
