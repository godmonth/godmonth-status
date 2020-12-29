package com.godmonth.status.builder.transitor;

import com.godmonth.status.analysis.impl.model.AnnotationBeanModelAnalysis;
import com.godmonth.status.analysis.impl.model.SimpleBeanModelAnalysis;
import com.godmonth.status.analysis.impl.sm.AnnotationStateMachineAnalysis;
import com.godmonth.status.analysis.impl.sm.SimpleStateMachineAnalysis;
import com.godmonth.status.builder.domain.SampleModel;
import com.godmonth.status.builder.domain.SampleStatus;
import com.godmonth.status.builder.domain.SampleTrigger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.util.function.Function;

/**
 * <p></p >
 *
 * @author shenyue
 */
class JsonDefinitionBuilderTest {
    @Test
    void jsonString() throws IOException {
        AnnotationBeanModelAnalysis<SampleModel> modelAnalysis = AnnotationBeanModelAnalysis.<SampleModel>annoBuilder().modelClass(SampleModel.class).build();
        AnnotationStateMachineAnalysis machineAnalysis = AnnotationStateMachineAnalysis.annoBuilder().modelAnalysis(modelAnalysis).build();
//        String jsonString = FileUtils.readFileToString(new File("src/test/resources/state-machine-sample.json"), StandardCharsets.UTF_8);
        Function<SampleStatus, Function<SampleTrigger, SampleStatus>> function = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().stateMachineAnalysis(machineAnalysis).resource(new FileSystemResource("src/test/resources/state-machine-sample.json")).build();
        System.out.println(function);
        Assertions.assertTrue(function.apply(SampleStatus.CREATED) != null);
        Assertions.assertTrue(function.apply(SampleStatus.CREATED).apply(SampleTrigger.PAY) != null);
        Assertions.assertEquals(function.apply(SampleStatus.CREATED).apply(SampleTrigger.PAY), SampleStatus.PAID);
    }

    @Test
    void genericType() throws IOException {
        AnnotationBeanModelAnalysis<SampleModel> modelAnalysis = AnnotationBeanModelAnalysis.<SampleModel>annoBuilder().modelClass(SampleModel.class).build();
        AnnotationStateMachineAnalysis machineAnalysis = AnnotationStateMachineAnalysis.annoBuilder().modelAnalysis(modelAnalysis).build();

        Function<SampleStatus, Function<SampleTrigger, SampleStatus>> function = JsonDefinitionBuilder.<SampleStatus, SampleTrigger>builder().resource(new FileSystemResource("src/test/resources/state-machine-sample.json")).stateMachineAnalysis(machineAnalysis).build();
        System.out.println(function);
        Assertions.assertTrue(function.apply(SampleStatus.CREATED) != null);
        Assertions.assertTrue(function.apply(SampleStatus.CREATED).apply(SampleTrigger.PAY) != null);
        Assertions.assertEquals(function.apply(SampleStatus.CREATED).apply(SampleTrigger.PAY), SampleStatus.PAID);
    }

    @Test
    void stringType() throws IOException {
        SimpleBeanModelAnalysis<SampleModel2> modelAnalysis = SimpleBeanModelAnalysis.<SampleModel2>builder().modelClass(SampleModel2.class).statusPropertyName("status").build();
        SimpleStateMachineAnalysis machineAnalysis = SimpleStateMachineAnalysis.builder().modelAnalysis(modelAnalysis).triggerClass(String.class).build();
        Function<String, Function<String, String>> function = JsonDefinitionBuilder.<String, String>builder().resource(new FileSystemResource("src/test/resources/state-machine-sample.json")).stateMachineAnalysis(machineAnalysis).build();
        System.out.println(function);
        Assertions.assertTrue(function.apply("CREATED") != null);
        Assertions.assertTrue(function.apply("CREATED").apply("PAY") != null);
        Assertions.assertEquals(function.apply("CREATED").apply("PAY"), "PAID");
    }
}