package com.godmonth.status.executor.intf;

import com.godmonth.status.advancer.intf.SyncResult;

import java.util.concurrent.Future;

/**
 * 订单执行
 *
 * @param <MODEL>
 * @param <INST>
 * @author shenyue
 */
public interface OrderExecutor2<MODEL, INST> {
    SyncResult<MODEL, ?> execute(ExecutionRequest<MODEL, INST> executionRequest);

    Future<SyncResult<MODEL, ?>> executeAsync(ExecutionRequest<MODEL, INST> executionRequest);
}
