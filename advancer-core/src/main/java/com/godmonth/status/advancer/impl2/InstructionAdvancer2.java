package com.godmonth.status.advancer.impl2;

import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.StatusAdvancer2;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

/**
 * 这种情况不用 {@link com.godmonth.status.annotations.InstructionBindingAnnotation}
 */
public abstract class InstructionAdvancer2<MODEL, INST, TRIGGER> implements StatusAdvancer2<MODEL, INST, TRIGGER> {

    @Setter
    protected INST expectedInstruction;

    @Override
    final public AdvancedResult<MODEL, TRIGGER> advance(MODEL model, INST instruction, Object message) {
        Validate.notNull(instruction, "instruction is null");
        Validate.isTrue(expectedInstruction == instruction, "expected instruction not equals input instruction.");
        return doAdvance(model, message);
    }

    protected abstract AdvancedResult<MODEL, TRIGGER> doAdvance(MODEL model, Object message);

}
