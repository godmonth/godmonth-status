package com.godmonth.commons.status.impl.executor;

import jodd.bean.BeanUtil;

public class DefaultOrderExecutor<MODEL, INST, TRIGGER, E extends Exception>
		extends AbstractOrderExecutor<MODEL, INST, TRIGGER, E> {

	protected String statusPropertyName;

	@Override
	protected Object getStatus(MODEL model) {
		return BeanUtil.silent.getProperty(model, statusPropertyName);
	}

	public void setStatusPropertyName(String statusPropertyName) {
		this.statusPropertyName = statusPropertyName;
	}

}
