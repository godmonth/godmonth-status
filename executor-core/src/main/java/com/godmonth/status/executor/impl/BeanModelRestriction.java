package com.godmonth.status.executor.impl;

public class BeanModelRestriction<V> {

	private String restrictionProperty;

	private V restrictionValue;

	public String getRestrictionProperty() {
		return restrictionProperty;
	}

	public void setRestrictionProperty(String restrictionProperty) {
		this.restrictionProperty = restrictionProperty;
	}

	public V getRestrictionValue() {
		return restrictionValue;
	}

	public void setRestrictionValue(V restrictionValue) {
		this.restrictionValue = restrictionValue;
	}

}
