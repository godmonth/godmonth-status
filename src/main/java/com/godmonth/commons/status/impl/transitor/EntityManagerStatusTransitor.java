package com.godmonth.commons.status.impl.transitor;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import com.godmonth.commons.status.intf.TransitionCallback;

public class EntityManagerStatusTransitor<TRIGGER, STATUS, MODEL, E extends Exception>
		extends AbstractStatusTransitor<TRIGGER, STATUS, MODEL, E> {

	@Autowired
	private EntityManager entityManager;

	protected TransactionTemplate transactionTemplate;

	@Override
	protected MODEL mergeModel(MODEL model, TransitionCallback<MODEL> transitionCallback) throws E {
		return transactionTemplate.execute((TransactionStatus status) -> {
			if (transitionCallback != null) {
				transitionCallback.beforeMerge(model);
			}
			return entityManager.merge(model);
		});

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
