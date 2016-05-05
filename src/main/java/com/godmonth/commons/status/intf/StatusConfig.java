package com.godmonth.commons.status.intf;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.godmonth.commons.status.impl.transitor.TriggerKey;

/**
 * 状态定义
 * 
 * @author shenyue
 *
 * @param <STATUS>
 * @param <TRIGGER>
 */
public class StatusConfig<STATUS, TRIGGER> {
	private STATUS status;

	private TRIGGER trigger;

	private STATUS nextStatus;

	public StatusConfig(STATUS status, TRIGGER trigger, STATUS nextStatus) {
		this.status = status;
		this.trigger = trigger;
		this.nextStatus = nextStatus;
	}

	public StatusConfig() {
		super();
	}

	public TriggerKey<STATUS, TRIGGER> getTriggerKey() {
		return new TriggerKey<>(status, trigger);
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

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
				.append("trigger", this.trigger).append("status", this.status).toString();
	}

}
