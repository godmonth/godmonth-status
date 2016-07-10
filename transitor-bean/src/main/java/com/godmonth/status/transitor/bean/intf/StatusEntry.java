package com.godmonth.status.transitor.bean.intf;

/**
 * 状态进入
 * 
 * @author shenyue
 *
 * @param <MODEL>
 */
public interface StatusEntry<MODEL> {
	
	void nextStatusEntry(MODEL model);
	
}
