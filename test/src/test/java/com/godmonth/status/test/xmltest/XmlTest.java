package com.godmonth.status.test.xmltest;

import com.godmonth.status.test.sample.repo.RepoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;

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
    @Test
    void name() {
        System.out.println("ok");
    }
}
