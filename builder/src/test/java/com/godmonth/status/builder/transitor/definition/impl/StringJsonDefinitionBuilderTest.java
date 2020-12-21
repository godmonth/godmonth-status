package com.godmonth.status.builder.transitor.definition.impl;

import com.godmonth.status.builder.statemachine.definition.impl.StringJsonDefinitionBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.Map;

/**
 * <p></p >
 *
 * @author shenyue
 */
class StringJsonDefinitionBuilderTest {
    @Test
    void name() {
        FileSystemResource fileSystemResource = new FileSystemResource(new File("src/test/resources/state-machine-sample.json"));
        System.out.println(fileSystemResource);

        Map<String, Map<String, String>> build = StringJsonDefinitionBuilder.buildConfigMap(fileSystemResource);
        System.out.println(build);
    }
}