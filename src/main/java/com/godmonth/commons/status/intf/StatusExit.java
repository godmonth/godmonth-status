package com.godmonth.commons.status.intf;

/**
 * 退出状态
 * 
 * @author shenyue
 *
 * @param <MODEL>
 */
public interface StatusExit<MODEL, E extends Exception> {
	void previousStatusExit(MODEL model) throws E;
}
