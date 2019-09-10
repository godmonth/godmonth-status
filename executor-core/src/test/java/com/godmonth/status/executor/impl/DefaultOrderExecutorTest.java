package com.godmonth.status.executor.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.test.sample.SampleConfigMap;
import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.test.sample.SampleStatus;
import com.godmonth.status.test.sample.SampleTrigger;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.tx.impl.AbstractTxStatusTransitor;
import com.godmonth.status.transitor.tx.intf.TransitionCallback;

public class DefaultOrderExecutorTest {

	private static final Logger logger = LoggerFactory.getLogger(DefaultOrderExecutorTest.class);

	private DefaultOrderExecutor<SampleModel, String, SampleTrigger> defaultOrderExecutor;

	@BeforeClass
	public void prepare() {

		BeanModelAnalysis<SampleModel, String, String> analysis = new BeanModelAnalysis<>();
		analysis.setExpectedTypeValue("test");
		analysis.setTypePropertyName("type");
		analysis.setStatusPropertyName("status");
		defaultOrderExecutor = new DefaultOrderExecutor<SampleModel, String, SampleTrigger>();
		defaultOrderExecutor.setModelAnalysis(analysis);
		defaultOrderExecutor.setAdvancerMappings(Collections.singletonMap(SampleStatus.CREATED, new PayAdvancer()));
		AbstractTxStatusTransitor<SampleModel, SampleStatus, SampleTrigger> abstractTxStatusTransitor = new AbstractTxStatusTransitor<SampleModel, SampleStatus, SampleTrigger>() {
			@Override
			protected SampleModel mergeModel(SampleModel model, SampleStatus nextStatus,
					TransitionCallback<SampleModel> transitionCallback) {
				return model;
			}
		};
		abstractTxStatusTransitor.setTransactionOperations(new TransactionOperations() {

			@Override
			public <T> T execute(TransactionCallback<T> action) throws TransactionException {
				return action.doInTransaction(null);
			}
		});
		SimpleStatusTransitor<SampleStatus, SampleTrigger> statusTransitor = new SimpleStatusTransitor<>(
				SampleConfigMap.INSTANCE);

		abstractTxStatusTransitor.setStatusTransitor(statusTransitor);
		abstractTxStatusTransitor.setStatusPropertyName("status");
		abstractTxStatusTransitor.setStatusEntryMap(Collections.singletonMap(SampleStatus.PAID, this::printEntry));
		abstractTxStatusTransitor.setStatusExitMap(Collections.singletonMap(SampleStatus.CREATED, this::printExit));
		defaultOrderExecutor.setTxStatusTransitor(abstractTxStatusTransitor);
	}

	private void printExit(SampleModel sampleStatus) {
		logger.debug("sampleStatus:{}", sampleStatus);
	}

	private void printEntry(SampleModel sampleStatus) {
		logger.debug("sampleStatus:{}", sampleStatus);
	}

	@Test
	public void execute() {
		SampleModel sampleModel = new SampleModel();
		sampleModel.setStatus(SampleStatus.CREATED);
		sampleModel.setType("test");
		SyncResult<SampleModel, ?> execute = defaultOrderExecutor.execute(sampleModel, "eee", "fff");
		Assert.assertEquals(execute.getModel().getStatus(), SampleStatus.PAID);
	}

	@Test
	public void execute2() {
		SampleModel sampleModel = new SampleModel();
		sampleModel.setStatus(SampleStatus.CREATED);
		sampleModel.setType("test");
		SyncResult<SampleModel, ?> execute = defaultOrderExecutor.execute(sampleModel, null, null);
		Assert.assertEquals(execute.getModel().getStatus(), SampleStatus.CREATED);
	}
}
