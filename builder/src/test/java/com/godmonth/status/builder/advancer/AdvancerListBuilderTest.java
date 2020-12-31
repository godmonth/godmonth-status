package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.builder.domain.SampleModel;
import com.godmonth.status.builder.domain.SampleStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.io.IOException;
import java.util.function.Function;

/**
 * <p></p >
 *
 * @author shenyue
 */
class AdvancerListBuilderTest {
    private StatusAdvancer statusAdvancer = new Ad1();

    @Test
    void name() throws IOException, ClassNotFoundException {
        AutowireCapableBeanFactory factory = Mockito.mock(AutowireCapableBeanFactory.class);
        Mockito.when(factory.autowire(Mockito.any(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(statusAdvancer);
        Function<Object, StatusAdvancer> function = AdvancerListBuilder.builder().autowireCapableBeanFactory(factory).modelClass(SampleModel.class).packageName("com.godmonth.status.builder.advancer").build();
        SampleModel sampleModel = new SampleModel();
        sampleModel.setStatus(SampleStatus.CREATED);
        StatusAdvancer apply = function.apply(SampleStatus.CREATED);
        AdvancedResult advancedResult = apply.advance(sampleModel, null, null);
        System.out.println(advancedResult);
    }
}