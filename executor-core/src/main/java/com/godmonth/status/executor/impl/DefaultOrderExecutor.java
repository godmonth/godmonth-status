package com.godmonth.status.executor.impl;

import jodd.bean.BeanUtil;

public class DefaultOrderExecutor<MODEL, INST, TRIGGER> extends AbstractOrderExecutor<MODEL, INST, TRIGGER> {

	protected String statusPropertyName;

	@Override
	protected Object getStatus(MODEL model) {
		return BeanUtil.silent.getProperty(model, statusPropertyName);
	}

	public void setStatusPropertyName(String statusPropertyName) {
		this.statusPropertyName = statusPropertyName;
	}

}
