package com.godmonth.status.transitor.definition.intf;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author shenyue
 *
 * @param <TRIGGER>
 * @param <STATUS>
 */
public class TriggerDefinition<TRIGGER, STATUS> {

	private TRIGGER trigger;

	private STATUS nextStatus;

	public TRIGGER getTrigger() {
		return trigger;
	}

	public void setTrigger(TRIGGER trigger) {
		this.trigger = trigger;
	}

	public STATUS getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(STATUS nextStatus) {
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
