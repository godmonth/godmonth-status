package com.godmonth.commons.status.intf;

/**
 * 事务内
 * 
 * @author shenyue
 *
 * @param <MODEL>
 */
public interface TransitionCallback<MODEL> {
	void beforeMerge(MODEL model);
}
