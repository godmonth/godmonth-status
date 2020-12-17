package com.godmonth.status.test.executor;

import org.apache.commons.lang3.Validate;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;

public class CheckAdvancer extends AbstractAdvancer<SampleModel, String, SampleTrigger> {
	{
		availableStatus = SampleStatus.PAID;
	}

	@Override
	public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, String instruction, Object message)
			throws IllegalStateException {
		Validate.isTrue(instruction == null && message == null);
		return null;
	}

}
