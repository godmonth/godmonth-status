package com.godmonth.commons.status.sample;

import com.godmonth.commons.status.impl.advancer.InstructionAdvancer;
import com.godmonth.commons.status.intf.AdvancedResult;
import com.godmonth.commons.status.intf.TriggerBehavior;

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
