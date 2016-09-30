package com.godmonth.status.transitor.tx.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;

import com.godmonth.status.transitor.tx.impl.AbstractTxStatusTransitor;
import com.godmonth.status.transitor.tx.intf.TransitionCallback;

public class JpaTxStatusTransitorImpl<MODEL, STATUS, TRIGGER>
		extends AbstractTxStatusTransitor<MODEL, STATUS, TRIGGER> {
	@Autowired
	private EntityManager entityManager;

	@Override
	protected MODEL mergeModel(MODEL model, STATUS nextStatus, TransitionCallback<MODEL> transitionCallback) {
		if (transitionCallback != null) {
			transitionCallback.beforeMerge(model);
		}
		return entityManager.merge(model);
	}

	@Override
	protected void lockModel(MODEL model) {
		entityManager.lock(model, LockModeType.PESSIMISTIC_WRITE);
	}

}
