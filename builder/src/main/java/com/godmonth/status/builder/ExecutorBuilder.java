package com.godmonth.status.builder;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.executor.impl.DefaultOrderExecutor;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.transitor.definition.intf.StatusDefinition;
import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class ExecutorBuilder {

    @Builder
    private <MODEL, INST, TRIGGER> OrderExecutor<MODEL, INST> build(Class<MODEL> modelClass,
                                                                    List<StatusAdvancer> statusAdvancers,
                                                                    Resource transitorConfig, Class<? extends StatusDefinition> statusDefinitionClass) {


        return new DefaultOrderExecutor<>();
    }

    private <MODEL> void abc(ObjectProvider<StatusAdvancer> statusAdvancers, Class<MODEL> modelClass) {
        List<StatusAdvancer> statusAdvancers1 = statusAdvancers.stream().filter(new Predicate<StatusAdvancer>() {
            @Override
            public boolean test(StatusAdvancer statusAdvancer) {
                Advancer annotation = statusAdvancer.getClass().getAnnotation(Advancer.class);
                return annotation != null && annotation.modelClass().equals(modelClass);
            }
        }).collect(Collectors.toList());
    }
}
