package com.godmonth.status.advancer.impl;

import com.godmonth.status.advancer.intf.AdvancedResult;
import org.apache.commons.lang3.Validate;

public abstract class InstructionAdvancer<MODEL, INST, TRIGGER> extends AbstractAdvancer<MODEL, INST, TRIGGER> {

	@Override
	final public AdvancedResult<MODEL, TRIGGER> advance(MODEL model, INST instruction, Object message) {
		Validate.notNull(instruction, "instruction is null");
		Validate.isTrue(expectedInstruction == instruction, "expected instruction not equals input instruction.");
		return doAdvance(model, message);
	}

	protected abstract AdvancedResult<MODEL, TRIGGER> doAdvance(MODEL model, Object message);

}
