package com.godmonth.commons.status.sample.advancer;

import com.godmonth.commons.status.impl.advancer.InstructionAdvancer;
import com.godmonth.commons.status.intf.AdvancedResult;
import com.godmonth.commons.status.intf.TriggerBehavior;
import com.godmonth.commons.status.sample.Activity;
import com.godmonth.commons.status.sample.ActivityInstruction;
import com.godmonth.commons.status.sample.ActivityStatus;
import com.godmonth.commons.status.sample.ActivityTrigger;

public class JumpAdvancer
		extends InstructionAdvancer<Activity, ActivityInstruction, ActivityTrigger, IllegalStateException> {

	{
		availableStatus = ActivityStatus.CREATED;
		expectedInstruction = ActivityInstruction.JUMP;
	}

	@Override
	protected AdvancedResult<Activity, ActivityTrigger> doAdvance(Activity model, Object message)
			throws IllegalStateException {
		System.out.println("advanced");
		return new AdvancedResult<>(new TriggerBehavior<>(ActivityTrigger.JUMP));
	}

}
