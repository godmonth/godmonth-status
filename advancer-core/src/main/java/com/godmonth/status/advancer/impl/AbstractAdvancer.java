package com.godmonth.status.advancer.impl;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @param <MODEL>
 * @param <INST>
 * @param <TRIGGER>
 * @deprecated use {@link com.godmonth.status.advancer.intf.StatusAdvancer2}
 */
@Deprecated
public abstract class AbstractAdvancer<MODEL, INST, TRIGGER> implements StatusAdvancer<MODEL, INST, TRIGGER> {
    @Setter
    protected Object availableStatus;
    @Setter
    protected INST expectedInstruction;

    @Override
    public Object getKey() {
        if (expectedInstruction != null) {
            return Pair.of(availableStatus, expectedInstruction);
        } else {
            return availableStatus;
        }
    }

}
