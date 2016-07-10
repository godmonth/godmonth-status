package com.godmonth.status.test.sample;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.godmonth.status.test.sample.SampleStatus;

public class SampleModel {
	private SampleStatus status;

	public SampleStatus getStatus() {
		return status;
	}

	public void setStatus(SampleStatus status) {
		this.status = status;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("status", this.status).toString();
	}

}
