package com.godmonth.status.transitor.tx.impl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;

import com.godmonth.status.transitor.bean.impl.BeanStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.TransitionCallback;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;

import jodd.bean.BeanUtil;

public abstract class AbstractTxStatusTransitor<MODEL, STATUS, TRIGGER>
		extends BeanStatusTransitorImpl<MODEL, STATUS, TRIGGER> implements TxStatusTransitor<MODEL, TRIGGER> {

	private TransactionOperations transactionOperations;

	@Override
	public MODEL transit(MODEL model, TriggerBehavior<TRIGGER, MODEL> triggerBehavior) {
		STATUS nextStatus = beforeChange(model, triggerBehavior.getTrigger());
		
		BeanUtil.silent.setProperty(model, statusPropertyName, nextStatus);

		MODEL mergedModel = transactionOperations.execute((TransactionStatus status) -> {
			return mergeModel(model, nextStatus, triggerBehavior.getTransitionCallback());
		});
		afterChange(mergedModel, nextStatus);

		return mergedModel;
	}

	protected abstract MODEL mergeModel(MODEL model, STATUS nextStatus, TransitionCallback<MODEL> transitionCallback);

	public void setTransactionOperations(TransactionOperations transactionOperations) {
		this.transactionOperations = transactionOperations;
	}

}
