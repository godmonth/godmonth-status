package com.godmonth.commons.status.impl.transitor;

import com.godmonth.commons.status.intf.TransitionCallback;

public class TransientStatusTransitor<TRIGGER, STATUS, MODEL, E extends Exception>
		extends AbstractStatusTransitor<TRIGGER, STATUS, MODEL, E> {

	@Override
	protected MODEL mergeModel(MODEL model, TransitionCallback<MODEL> transitionCallback) throws E {
		if (transitionCallback != null) {
			transitionCallback.beforeMerge(model);
		}
		return model;
	}

}
