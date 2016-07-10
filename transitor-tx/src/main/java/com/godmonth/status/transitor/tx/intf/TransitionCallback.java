package com.godmonth.status.transitor.tx.intf;

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
