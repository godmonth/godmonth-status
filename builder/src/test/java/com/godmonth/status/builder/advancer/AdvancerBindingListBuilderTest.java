package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.AdvancerBinding;
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
class AdvancerBindingListBuilderTest {
    private StatusAdvancer statusAdvancer = new PayAdvancer();

    @Test
    void name() throws IOException, ClassNotFoundException {
        AutowireCapableBeanFactory factory = Mockito.mock(AutowireCapableBeanFactory.class);
        Mockito.when(factory.autowire(Mockito.any(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(statusAdvancer);
        List<AdvancerBinding> build = AdvancerBindingListBuilder.builder().autowireCapableBeanFactory(factory).modelClass(SampleModel.class).packageName("com.godmonth.status.builder.advancer").build();
        System.out.println(build);
    }

}