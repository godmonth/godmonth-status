package com.godmonth.commons.status.sample;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActivityStatusMachine {

	public static final Map<ActivityStatus, Map<ActivityTrigger, ActivityStatus>> INSTANCE;
	static {
		Map<ActivityStatus, Map<ActivityTrigger, ActivityStatus>> sm = new HashMap<>();
		{
			Map<ActivityTrigger, ActivityStatus> triggerOperation = new HashMap<>();
			sm.put(ActivityStatus.CREATED, triggerOperation);

			triggerOperation.put(ActivityTrigger.ENROLL, ActivityStatus.ENROLLED);
			triggerOperation.put(ActivityTrigger.JUMP, ActivityStatus.CHOSEN);

		}
		INSTANCE = Collections.unmodifiableMap(sm);
	}

	private ActivityStatusMachine() {
	}

}
