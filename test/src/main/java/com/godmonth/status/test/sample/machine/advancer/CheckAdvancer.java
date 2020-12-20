package com.godmonth.status.test.sample.machine.advancer;

import org.apache.commons.lang3.Validate;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.domain.SampleTrigger;

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
