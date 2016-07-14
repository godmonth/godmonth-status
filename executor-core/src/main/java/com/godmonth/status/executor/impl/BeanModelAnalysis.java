package com.godmonth.status.executor.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.godmonth.status.executor.intf.ModelAnalysis;

import jodd.bean.BeanUtil;

public class BeanModelAnalysis<MODEL, VALUE, STATUS> implements ModelAnalysis<MODEL, STATUS> {

	private Class<MODEL> modelClass;

	private VALUE expectedTypeValue;

	private String typePropertyName;

	private String statusPropertyName;

	@Override
	public void validate(MODEL model) {
		if (modelClass != null) {
			Validate.isTrue(modelClass.equals(model.getClass()));
		}
		if (StringUtils.isNotBlank(typePropertyName) && expectedTypeValue != null) {
			VALUE actualValue = BeanUtil.silent.getProperty(model, typePropertyName);
			Validate.isTrue(expectedTypeValue.equals(actualValue), "expected:%s,actual:%s", expectedTypeValue,
					actualValue);
		}
	}

	@Override
	public STATUS getStatus(MODEL model) {
		return BeanUtil.silent.getProperty(model, statusPropertyName);
	}

	public void setExpectedTypeValue(VALUE expectedTypeValue) {
		this.expectedTypeValue = expectedTypeValue;
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
