package com.godmonth.status.transitor.bean.intf;

/**
 * 退出状态
 * 
 * @author shenyue
 *
 * @param <MODEL>
 */
public interface StatusExit<MODEL> {
	void previousStatusExit(MODEL model);
}
