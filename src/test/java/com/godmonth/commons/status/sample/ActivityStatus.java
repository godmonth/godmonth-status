package com.godmonth.commons.status.sample;

/**
 * 活动状态
 * 
 * @author shenyue
 *
 */
public enum ActivityStatus {
	/**
	 * 
	 */
	CREATED("已创建"),

	/**
	 * 
	 */
	OPENED("已开放"),

	/**
	 * 
	 */
	ENROLLED("报名截止"),

	/**
	 * 
	 */
	CHOSEN("已选择"),

	/**
	 * 
	 */
	PRESSED("已截稿");

	private String description;

	private ActivityStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
