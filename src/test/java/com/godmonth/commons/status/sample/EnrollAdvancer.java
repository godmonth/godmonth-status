package com.godmonth.commons.status.sample;

import com.godmonth.commons.status.impl.advancer.AbstractAdvancer;
import com.godmonth.commons.status.intf.AdvancedResult;
import com.godmonth.commons.status.intf.TriggerBehavior;

public class EnrollAdvancer
		extends AbstractAdvancer<Activity, ActivityInstruction, ActivityTrigger, IllegalStateException> {
	{
		availableStatus = ActivityStatus.CREATED;
	}

	@Override
	public AdvancedResult<Activity, ActivityTrigger> advance(Activity model, ActivityInstruction instruction,
			Object message) throws IllegalStateException {
		System.out.println("advanced");
		return new AdvancedResult<>(new TriggerBehavior<>(ActivityTrigger.ENROLL));
	}

}
