package com.godmonth.status.test.xmltest;

import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.repo.RepoConfig;
import com.godmonth.status.test.sample.repo.SampleModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;

/**
 * <p></p >
 *
 * @author shenyue
 */
@AutoConfigureDataJpa
@SpringBootTest(classes = {RepoConfig.class})
@ImportResource(locations = {"classpath:/sample-bean.xml"})
@EnableAutoConfiguration
public class XmlTest {
    @Autowired
    private SampleModelRepository sampleModelRepository;
    @Resource(name = "sampleModelExecutor")
    private OrderExecutor<SampleModel, String> sampleModelExecutor;

    @Test
    void name() {
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        sampleModel.setType("test");
        SampleModel sampleModel1 = sampleModelRepository.save(sampleModel);
        SyncResult<SampleModel, ?> execute = sampleModelExecutor.execute(sampleModel, null, null);
        System.out.println(execute);
    }
}
