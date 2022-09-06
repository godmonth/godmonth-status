package com.godmonth.status.executor.intf;

import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;

import java.util.concurrent.Future;

/**
 * 订单执行
 *
 * @param <MODEL>
 * @param <INST>  指令
 * @author shenyue
 */
public interface OrderExecutor<MODEL, INST> {
    SyncResult<MODEL, ?> execute(MODEL model, INST instruction, Object param);

    Future<SyncResult<MODEL, ?>> executeAsync(MODEL model, INST instruction, Object param);

    <TRIGGER> TxStatusTransitor<MODEL, TRIGGER> getTxStatusTransitor();
}
