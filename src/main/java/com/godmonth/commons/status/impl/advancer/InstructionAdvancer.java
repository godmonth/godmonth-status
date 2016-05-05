package com.godmonth.commons.status.impl.advancer;

import org.apache.commons.lang3.Validate;

import com.godmonth.commons.status.intf.AdvancedResult;

public abstract class InstructionAdvancer<MODEL, INST, TRIGGER, E extends Exception>
		extends AbstractAdvancer<MODEL, INST, TRIGGER, E> {

	@Override
	final public AdvancedResult<MODEL, TRIGGER> advance(MODEL model, INST instruction, Object message) throws E {
		Validate.notNull(instruction, "instruction is null");
		Validate.isTrue(expectedInstruction == instruction, "expected instruction not equals input instruction.");
		return doAdvance(model, message);
	}

	protected abstract AdvancedResult<MODEL, TRIGGER> doAdvance(MODEL model, Object message) throws E;

}
