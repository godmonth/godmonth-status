package com.godmonth.status.advancer.impl;

import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;

/**
 * @author shenyue
 *
 */
public class DoNothingAdvancer<MODEL, INST, TRIGGER> extends InstructionAdvancer<MODEL, INST, TRIGGER> {

	private TRIGGER trigger;

	@Override
	protected AdvancedResult<MODEL, TRIGGER> doAdvance(MODEL model, Object message) {
		return new AdvancedResult<>(new TriggerBehavior<TRIGGER, MODEL>(trigger));
	}

	public void setTrigger(TRIGGER trigger) {
		this.trigger = trigger;
	}

	public TRIGGER getTrigger() {
		return trigger;
	}

}
