package com.godmonth.status.executor.intf;

import java.util.concurrent.Future;

import com.godmonth.status.advancer.intf.SyncResult;

/**
 * 订单执行
 * 
 * @author shenyue
 *
 * @param <MODEL>
 * @param <INST>
 *            指令
 */
public interface OrderExecutor<MODEL, INST> {
	SyncResult<MODEL, ?> execute(MODEL model, INST instruction, Object param);

	Future<SyncResult<MODEL, ?>> executeAsync(MODEL model, INST instruction, Object param);
}
