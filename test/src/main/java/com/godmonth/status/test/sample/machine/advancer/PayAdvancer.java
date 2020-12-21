package com.godmonth.status.test.sample.machine.advancer;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.NextOperation;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;

public class PayAdvancer extends AbstractAdvancer<SampleModel, String, SampleTrigger> {
	{
		availableStatus = SampleStatus.CREATED;
	}

	@Override
	public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, String instruction, Object message)
			throws IllegalStateException {
		System.out.println("advanced");
		if ("eee".equals(instruction) && "fff".equals(message)) {
			return new AdvancedResult<>(new TriggerBehavior<>(SampleTrigger.PAY), NextOperation.ADVANCE, true);
		}
		return null;

	}

}
