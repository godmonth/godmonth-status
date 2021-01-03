package com.godmonth.status.advancer.impl2;

import com.godmonth.status.advancer.intf.AdvancerBinding;
import com.godmonth.status.advancer.intf.StatusAdvancer;
import lombok.AccessLevel;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;

@Setter
public class AdvancerBindingFactoryBean implements FactoryBean<AdvancerBinding> {
    protected Object availableStatus;

    protected Object expectedInstruction;

    protected StatusAdvancer statusAdvancer;

    @Setter(value = AccessLevel.NONE)
    protected AdvancerBinding advancerBinding;

    public Object getKey() {

        if (expectedInstruction != null) {
            return Pair.of(availableStatus, expectedInstruction);
        } else {
            return availableStatus;
        }
    }

    @PostConstruct
    public void init() {
        Object key = getKey();
        advancerBinding = AdvancerBinding.builder().key(key).statusAdvancer(statusAdvancer).build();
    }

    @Override
    public AdvancerBinding getObject() throws Exception {
        return advancerBinding;
    }

    @Override
    public Class<?> getObjectType() {
        return AdvancerBinding.class;
    }
}
