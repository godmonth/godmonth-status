package com.godmonth.status.test.sample.machine;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:/sample-bean.xml"})
public class StatusMachineConfig {

}
