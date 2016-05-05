package com.godmonth.commons.status.intf;

/**
 * 状态推进器
 * 
 * @author shenyue
 *
 * @param <MODEL>
 *            MODEL
 */
public interface StatusAdvancer<MODEL, INST, TRIGGER, E extends Exception> {
	AdvancedResult<MODEL, TRIGGER> advance(MODEL model, INST instruction, Object message) throws E;

	Object getKey();
}
