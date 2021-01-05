package com.godmonth.status.advancer.impl2;

import com.godmonth.status.advancer.intf.AdvancerBinding;
import com.godmonth.status.advancer.intf.StatusAdvancer2;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;

@Setter
public class AdvancerBindingFactoryBean implements FactoryBean<Pair<Object, StatusAdvancer2>> {

    @Setter
    protected Object availableStatus;

    @Setter
    protected Object expectedInstruction;

    @Setter
    protected StatusAdvancer2 statusAdvancer;

    protected Pair<Object, StatusAdvancer2> advancerBinding;

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
        advancerBinding = Pair.of(key, statusAdvancer);
    }

    @Override
    public Pair<Object, StatusAdvancer2> getObject() throws Exception {
        return advancerBinding;
    }

    @Override
    public Class<?> getObjectType() {
        return AdvancerBinding.class;
    }
}
