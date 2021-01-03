package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.builder.domain.SampleModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.io.IOException;
import java.util.List;

/**
 * <p></p >
 *
 * @author shenyue
 */
class AdvancerListBuilderTest {
    private StatusAdvancer statusAdvancer = new StatusAdvancer() {
        @Override
        public AdvancedResult advance(Object o, Object instruction, Object message) {
            return null;
        }

        @Override
        public Object getKey() {
            return null;
        }
    };

    @Test
    void name() throws IOException, ClassNotFoundException {
        AutowireCapableBeanFactory factory = Mockito.mock(AutowireCapableBeanFactory.class);
        Mockito.when(factory.autowire(Mockito.any(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(statusAdvancer);
        List<StatusAdvancer> list = AdvancerListBuilder.builder().autowireCapableBeanFactory(factory).modelClass(SampleModel.class).packageName("com.godmonth.status.builder.advancer").build();

    }
}