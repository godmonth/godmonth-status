package com.godmonth.status.test.sample2;

import com.godmonth.status.test.sample.repo.RepoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p></p >
 *
 * @author shenyue
 */
@AutoConfigureDataJpa
@SpringBootTest(classes = {RepoConfig.class, SampleConfig.class})
@EnableAutoConfiguration
public class Sample2Test {
    @Test
    void name() {
        System.out.println("ok");
    }
}
