package status.advancer.impl;

import status.advancer.intf.StatusAdvancer;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

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
