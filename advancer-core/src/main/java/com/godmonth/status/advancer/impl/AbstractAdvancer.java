package com.godmonth.status.advancer.impl;

import org.apache.commons.lang3.tuple.Pair;

import com.godmonth.status.advancer.intf.StatusAdvancer;

public abstract class AbstractAdvancer<MODEL, INST, TRIGGER> implements StatusAdvancer<MODEL, INST, TRIGGER> {
	protected Object availableStatus;

	protected INST expectedInstruction;

	@Override
	public Object getKey() {
		if (expectedInstruction != null) {
			return Pair.of(availableStatus, expectedInstruction);
		} else {
			return availableStatus;
		}
	}

	public void setExpectedInstruction(INST expectedInstruction) {
		this.expectedInstruction = expectedInstruction;
	}

	public void setAvailableStatus(Object availableStatus) {
		this.availableStatus = availableStatus;
	}

}
