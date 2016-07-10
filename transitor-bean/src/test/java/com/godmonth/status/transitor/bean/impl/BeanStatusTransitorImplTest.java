package com.godmonth.status.transitor.bean.impl;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.godmonth.status.test.sample.SampleConfigMap;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;

public class BeanStatusTransitorImplTest {

	private static final Logger logger = LoggerFactory.getLogger(BeanStatusTransitorImplTest.class);

	private BeanStatusTransitorImpl<SampleModel, SampleStatus, SampleTrigger> beanStatusTransitor = new BeanStatusTransitorImpl<>();

	@BeforeClass
	public void prepare() throws IOException {
		SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
				SampleConfigMap.INSTANCE);
		beanStatusTransitor.setStatusTransitor(statusTransitor);
		beanStatusTransitor.setStatusPropertyName("status");
		beanStatusTransitor.setStatusEntryMap(Collections.singletonMap(SampleStatus.PAID, this::printEntry));
		beanStatusTransitor.setStatusExitMap(Collections.singletonMap(SampleStatus.CREATED, this::printExit));
	}

	private void printExit(SampleModel sampleStatus) {
		logger.debug("sampleStatus:{}", sampleStatus);
	}

	private void printEntry(SampleModel sampleStatus) {
		logger.debug("sampleStatus:{}", sampleStatus);
	}

	@Test
	public void transit() {
		SampleModel sampleModel = new SampleModel();
		sampleModel.setStatus(SampleStatus.CREATED);
		SampleModel transit = beanStatusTransitor.transit(sampleModel, SampleTrigger.PAY);
		Assert.assertEquals(transit.getStatus(), SampleStatus.PAID);
		System.out.println(transit);
	}
}
