package com.godmonth.status.test.sample.domain;

import java.util.HashMap;
import java.util.Map;

public class SampleConfigMap {
	public static final Map<SampleStatus, Map<SampleTrigger, SampleStatus>> INSTANCE;
	static {
		Map<SampleTrigger, SampleStatus> payTriggerConfig = new HashMap<>();
		payTriggerConfig.put(SampleTrigger.PAY, SampleStatus.PAID);

		INSTANCE = new HashMap<SampleStatus, Map<SampleTrigger, SampleStatus>>();
		INSTANCE.put(SampleStatus.CREATED, payTriggerConfig);
	}
}
