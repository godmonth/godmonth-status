package com.godmonth.status.test.sample.repo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.godmonth.status.test.sample")
@EnableJpaRepositories
public class RepoConfig {

}
