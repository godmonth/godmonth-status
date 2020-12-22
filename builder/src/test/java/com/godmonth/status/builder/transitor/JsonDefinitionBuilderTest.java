package com.godmonth.status.builder.transitor;

import com.godmonth.status.builder.domain.SampleStatus;
import com.godmonth.status.builder.domain.SampleTrigger;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p></p >
 *
 * @author shenyue
 */
class JsonDefinitionBuilderTest {
    @Test
    void jsonString() throws IOException {
        String jsonString = FileUtils.readFileToString(new File("src/test/resources/state-machine-sample.json"), StandardCharsets.UTF_8);
        Map<SampleStatus, Map<SampleTrigger, SampleStatus>> map = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().jsonString(jsonString).statusClass(SampleStatus.class).triggerClass(SampleTrigger.class).build();
        System.out.println(map);
        Assertions.assertTrue(map.containsKey(SampleStatus.CREATED));
        Assertions.assertTrue(map.get(SampleStatus.CREATED).containsKey(SampleTrigger.PAY));
        Assertions.assertEquals(map.get(SampleStatus.CREATED).get(SampleTrigger.PAY), SampleStatus.PAID);
    }

    @Test
    void genericType() throws IOException {
        Map<SampleStatus, Map<SampleTrigger, SampleStatus>> map = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().resource(new FileSystemResource("src/test/resources/state-machine-sample.json")).statusClass(SampleStatus.class).triggerClass(SampleTrigger.class).build();
        System.out.println(map);
        Assertions.assertTrue(map.containsKey(SampleStatus.CREATED));
        Assertions.assertTrue(map.get(SampleStatus.CREATED).containsKey(SampleTrigger.PAY));
        Assertions.assertEquals(map.get(SampleStatus.CREATED).get(SampleTrigger.PAY), SampleStatus.PAID);
    }

    @Test
    void stringType() throws IOException {
        Map<String, Map<String, String>> map = JsonDefinitionBuilder.<String, String>builder().resource(new FileSystemResource("src/test/resources/state-machine-sample.json")).statusClass(String.class).triggerClass(String.class).build();
        System.out.println(map);
        Assertions.assertTrue(map.containsKey("CREATED"));
        Assertions.assertTrue(map.get("CREATED").containsKey("PAY"));
        Assertions.assertEquals(map.get("CREATED").get("PAY"), "PAID");
    }
}