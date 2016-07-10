package com.godmonth.status.executor.impl;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;

public class PayAdvancer extends AbstractAdvancer<SampleModel, Void, SampleTrigger> {
	{
		availableStatus = SampleStatus.CREATED;
	}

	@Override
	public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, Void instruction, Object message)
			throws IllegalStateException {
		System.out.println("advanced");
		return new AdvancedResult<>(new TriggerBehavior<>(SampleTrigger.PAY));
	}

}
