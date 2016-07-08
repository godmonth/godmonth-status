package com.godmonth.commons.status.impl.transitor.definition;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TriggerDefinition<T, S> {
	
	private T trigger;
	
	private S nextStatus;

	public T getTrigger() {
		return trigger;
	}

	public void setTrigger(T trigger) {
		this.trigger = trigger;
	}

	public S getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(S nextStatus) {
		this.nextStatus = nextStatus;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("nextStatus", this.nextStatus)
				.append("trigger", this.trigger).toString();
	}

}
