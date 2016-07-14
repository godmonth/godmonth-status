package com.godmonth.status.executor.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.godmonth.status.executor.intf.ModelAnalysis;

import jodd.bean.BeanUtil;

public class BeanModelAnalysis<MODEL, VALUE, STATUS> implements ModelAnalysis<MODEL, STATUS> {

	private Class<MODEL> modelClass;

	private VALUE expectedValue;

	private String typePropertyName;

	private String statusPropertyName;

	@Override
	public void validate(MODEL model) {
		if (modelClass != null) {
			Validate.isTrue(modelClass.equals(model.getClass()));
		}
		if (StringUtils.isNotBlank(typePropertyName) && expectedValue != null) {
			VALUE actualValue = BeanUtil.silent.getProperty(model, typePropertyName);
			Validate.isTrue(expectedValue.equals(actualValue), "expected:%s,actual:%s", expectedValue, actualValue);
		}
	}

	@Override
	public STATUS getStatus(MODEL model) {
		return BeanUtil.silent.getProperty(model, statusPropertyName);
	}

	public void setExpectedValue(VALUE expectedValue) {
		this.expectedValue = expectedValue;
	}

	public void setTypePropertyName(String typePropertyName) {
		this.typePropertyName = typePropertyName;
	}

	public void setStatusPropertyName(String statusPropertyName) {
		this.statusPropertyName = statusPropertyName;
	}

	public void setModelClass(Class<MODEL> modelClass) {
		this.modelClass = modelClass;
	}

}
