package com.godmonth.commons.status.impl.advancer;

import org.apache.commons.lang3.tuple.Pair;

import com.godmonth.commons.status.intf.StatusAdvancer;

public abstract class AbstractAdvancer<MODEL, INST, TRIGGER, E extends Exception>
		implements StatusAdvancer<MODEL, INST, TRIGGER, E> {
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

	public void setAvailableStatus(String availableStatus) {
		this.availableStatus = availableStatus;
	}

}
