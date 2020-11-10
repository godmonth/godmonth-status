package com.godmonth.status.transitor.core.impl.definition;

import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleStatusDefinition;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.definition.impl.JsonDefinition;
import com.godmonth.status.transitor.definition.intf.StringStatusDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonDefinitionTest {

    @Test
    public void parseString() throws IOException {
        String readFileToString = FileUtils.readFileToString(new File("src/test/resources/state-machine-sample.json"),
                StandardCharsets.UTF_8);
        Map<String, Map<String, String>> parse = JsonDefinition.parse(readFileToString, StringStatusDefinition.class);
        System.out.println(parse);
        Assertions.assertTrue(parse.containsKey("CREATED"));
        Assertions.assertTrue(parse.get("CREATED").containsKey("PAY"));
        Assertions.assertEquals(parse.get("CREATED").get("PAY"), "PAID");
    }

    @Test
    public void parseSample() throws IOException {
        String readFileToString = FileUtils.readFileToString(new File("src/test/resources/state-machine-sample.json"),
                StandardCharsets.UTF_8);
        Map<SampleStatus, Map<SampleTrigger, SampleStatus>> parse = JsonDefinition.parse(readFileToString,
                SampleStatusDefinition.class);
        System.out.println(parse);
        Assertions.assertTrue(parse.containsKey(SampleStatus.CREATED));
        Assertions.assertTrue(parse.get(SampleStatus.CREATED).containsKey(SampleTrigger.PAY));
        Assertions.assertEquals(parse.get(SampleStatus.CREATED).get(SampleTrigger.PAY), SampleStatus.PAID);
    }

}
