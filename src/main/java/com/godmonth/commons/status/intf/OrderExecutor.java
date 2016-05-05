package com.godmonth.commons.status.intf;

import java.util.concurrent.Future;

/**
 * 订单执行
 * 
 * @author shenyue
 *
 * @param <MODEL>
 * @param <INST>
 *            指令
 */
public interface OrderExecutor<MODEL, INST, E extends Exception> {
	SyncResult<MODEL, ?> execute(MODEL model, INST instruction, Object param) throws E;

	Future<SyncResult<MODEL, ?>> executeAsync(MODEL model, INST instruction, Object param) throws E;
}
