package com.godmonth.status.transitor.core.impl;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.godmonth.status.test.sample.SampleConfigMap;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;

public class SimpleStatusTransitorTest {
	private SimpleStatusTransitor<SampleStatus, SampleTrigger> simpleStatusTransitor;

	@BeforeClass
	public void prepare() throws IOException {
		simpleStatusTransitor = new SimpleStatusTransitor<>(SampleConfigMap.INSTANCE);
	}

	@Test
	public void transit() {
		SampleStatus transit = simpleStatusTransitor.transit(SampleStatus.CREATED, SampleTrigger.PAY);
		Assert.assertEquals(transit, SampleStatus.PAID);
	}

}
