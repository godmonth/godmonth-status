package com.godmonth.commons.status.impl.transitor;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TriggerKey<STATUS, TRIGGER> {
	private final STATUS status;

	private final TRIGGER trigger;

	public TriggerKey(STATUS status, TRIGGER trigger) {
		this.status = status;
		this.trigger = trigger;
	}

	public STATUS getStatus() {
		return status;
	}

	public TRIGGER getTrigger() {
		return trigger;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@SuppressWarnings("rawtypes")
	public boolean equals(Object object) {
		if (!(object instanceof TriggerKey)) {
			return false;
		}
		TriggerKey rhs = (TriggerKey) object;
		return new EqualsBuilder().append(this.trigger, rhs.trigger).append(this.status, rhs.status).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(605769525, -2005738275).append(this.trigger).append(this.status).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("status", this.status)
				.append("trigger", this.trigger).toString();
	}

}
