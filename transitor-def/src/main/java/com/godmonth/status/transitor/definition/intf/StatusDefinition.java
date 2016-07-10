package com.godmonth.status.transitor.definition.intf;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 状态定义
 * 
 * @author shenyue
 *
 * @param <STATUS>
 * @param <TRIGGER>
 * @param <TD>
 */
public class StatusDefinition<STATUS, TRIGGER> {
	private STATUS status;

	private List<TriggerDefinition<TRIGGER, STATUS>> triggers;

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public List<TriggerDefinition<TRIGGER, STATUS>> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<TriggerDefinition<TRIGGER, STATUS>> triggers) {
		this.triggers = triggers;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("triggers", this.triggers).append("status", this.status).toString();
	}

}
