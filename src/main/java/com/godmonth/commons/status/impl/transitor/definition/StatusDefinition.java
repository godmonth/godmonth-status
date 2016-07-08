package com.godmonth.commons.status.impl.transitor.definition;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StatusDefinition<STATUS, TRIGGER> {
	private STATUS status;

	private List<TriggerDefinition<TRIGGER, STATUS>> triggerDefinitions;

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public List<TriggerDefinition<TRIGGER, STATUS>> getTriggerDefinitions() {
		return triggerDefinitions;
	}

	public void setTriggerDefinitions(List<TriggerDefinition<TRIGGER, STATUS>> triggerDefinitions) {
		this.triggerDefinitions = triggerDefinitions;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("triggerParams", this.triggerDefinitions).append("status", this.status).toString();
	}

}
