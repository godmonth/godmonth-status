package com.godmonth.commons.status.impl.transitor;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.godmonth.commons.status.intf.StatusConfig;
import com.godmonth.commons.status.intf.StatusEntry;
import com.godmonth.commons.status.intf.StatusExit;

public class TransitionConfig<STATUS, TRIGGER, MODEL, E extends Exception> {

	private StatusConfig<STATUS, TRIGGER> statusConfig;

	private StatusEntry<MODEL, E> statusEntry;

	private StatusExit<MODEL, E> statusExit;

	public StatusConfig<STATUS, TRIGGER> getStatusConfig() {
		return statusConfig;
	}

	public void setStatusConfig(StatusConfig<STATUS, TRIGGER> statusConfig) {
		this.statusConfig = statusConfig;
	}

	public StatusEntry<MODEL, E> getStatusEntry() {
		return statusEntry;
	}

	public StatusExit<MODEL, E> getStatusExit() {
		return statusExit;
	}

	public void setStatusEntry(StatusEntry<MODEL, E> statusEntry) {
		this.statusEntry = statusEntry;
	}

	public void setStatusExit(StatusExit<MODEL, E> statusExit) {
		this.statusExit = statusExit;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("statusEntry", this.statusEntry)
				.append("statusExit", this.statusExit).append("statusConfig", this.statusConfig).toString();
	}

}
