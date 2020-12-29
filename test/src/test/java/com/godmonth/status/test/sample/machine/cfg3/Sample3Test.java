package com.godmonth.status.test.sample.machine.cfg3;

import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.test.sample.db2.RepoConfig2;
import com.godmonth.status.test.sample.db3.RepoConfig3;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.repo.SampleModelRepository;
import com.godmonth.status.test.sample.repo2.SampleModelReadOnlyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ComponentScan
@SpringBootTest(classes = {RepoConfig2.class, RepoConfig3.class, SampleOrderExecutorConfig3.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class Sample3Test {

    @Autowired
    private OrderExecutor<SampleModel, String> sampleModelOrderExecutor;

    @Autowired
    private SampleModelRepository sampleModelRepository;

    @Autowired
    private SampleModelReadOnlyRepository sampleModelReadOnlyRepository;

    @Qualifier("d2")
    @Autowired
    private DataSource dataSource2;

    @Qualifier("d3")
    @Autowired
    private DataSource dataSource3;

    @Test
    void name() throws SQLException {
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        sampleModel.setType("test");
        SampleModel sampleModel1 = sampleModelRepository.save(sampleModel);
        SyncResult<SampleModel, ?> execute = sampleModelOrderExecutor.execute(sampleModel1, "eee", "fff");
        System.out.println(execute);
        {
            Connection connection = dataSource2.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select count(*) from sample_model");
            resultSet.next();
            Assertions.assertEquals(resultSet.getInt(1), 1);
            String string = resultSet.getString(1);
            System.out.println(string);
        }
        {
            //查看数据源3
            Connection connection3 = dataSource3.getConnection();
            ResultSet resultSet3 = connection3.createStatement().executeQuery("select count(*) from sample_model");
            resultSet3.next();
            Assertions.assertEquals(resultSet3.getInt(1), 0);
        }
        //查看数据源2
        Optional<SampleModel> byId = sampleModelRepository.findById(1L);
        Assertions.assertTrue(byId.isPresent());
        Optional<SampleModel> byId2 = sampleModelReadOnlyRepository.findById(1L);
        Assertions.assertFalse(byId2.isPresent());

    }
}
