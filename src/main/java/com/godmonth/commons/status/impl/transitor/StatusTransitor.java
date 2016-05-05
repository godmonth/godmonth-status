package com.godmonth.commons.status.impl.transitor;

import com.godmonth.commons.status.intf.TriggerBehavior;

/**
 * 状态跃迁器
 * 
 * @author shenyue
 *
 */
public interface StatusTransitor<TRIGGER, MODEL, E extends Exception> {

	MODEL transit(MODEL model, TriggerBehavior<TRIGGER, MODEL> triggerBehavior) throws E;

}
