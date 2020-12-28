package com.godmonth.status.test.sample.machine.cfg3;

import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.test.sample.db2.RepoConfig2;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.repo.SampleModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ComponentScan
@SpringBootTest(classes = {RepoConfig2.class, SampleOrderExecutorConfig3.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class Sample3Test {

    @Autowired
    private OrderExecutor<SampleModel, String> sampleModelOrderExecutor;

    @Autowired
    private SampleModelRepository sampleModelRepository;

    @Test
    void name() {
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        sampleModel.setType("test");
        SampleModel sampleModel1 = sampleModelRepository.save(sampleModel);
        SyncResult<SampleModel, ?> execute = sampleModelOrderExecutor.execute(sampleModel1, "eee", "fff");
        System.out.println(execute);
    }
}
