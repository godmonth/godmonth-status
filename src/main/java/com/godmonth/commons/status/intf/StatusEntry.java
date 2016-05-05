package com.godmonth.commons.status.intf;

/**
 * 状态进入
 * 
 * @author shenyue
 *
 * @param <MODEL>
 */
public interface StatusEntry<MODEL, E extends Exception> {
	void nextStatusEntry(MODEL model) throws E;
}
