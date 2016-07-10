package com.godmonth.status.transitor.tx.intf;

/**
 * 状态跃迁器
 * 
 * @author shenyue
 *
 * @param <MODEL>
 * @param <TRIGGER>
 */
public interface TxStatusTransitor<MODEL, TRIGGER> {

	MODEL transit(MODEL model, TriggerBehavior<TRIGGER, MODEL> triggerBehavior);

}
