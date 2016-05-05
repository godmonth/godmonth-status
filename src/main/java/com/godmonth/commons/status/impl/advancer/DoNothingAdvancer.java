package com.godmonth.commons.status.impl.advancer;

import com.godmonth.commons.status.intf.AdvancedResult;
import com.godmonth.commons.status.intf.TriggerBehavior;

/**
 * @author shenyue
 *
 */
public class DoNothingAdvancer<MODEL, TRIGGER, INST, E extends Exception>
		extends InstructionAdvancer<MODEL, INST, TRIGGER, E> {

	private TRIGGER trigger;

	@Override
	protected AdvancedResult<MODEL, TRIGGER> doAdvance(MODEL model, Object message) throws E {
		return new AdvancedResult<>(new TriggerBehavior<TRIGGER, MODEL>(trigger));
	}

	public void setTrigger(TRIGGER trigger) {
		this.trigger = trigger;
	}

}
