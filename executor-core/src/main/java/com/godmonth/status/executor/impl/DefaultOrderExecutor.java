package com.godmonth.status.executor.impl;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.executor.intf.ModelAnalysis;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;

public class DefaultOrderExecutor<MODEL, INST, TRIGGER> implements OrderExecutor<MODEL, INST> {

	private static final Logger logger = LoggerFactory.getLogger(DefaultOrderExecutor.class);

	private Map<Object, StatusAdvancer<MODEL, INST, TRIGGER>> advancerMappings;

	private TxStatusTransitor<MODEL, TRIGGER> txStatusTransitor;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private ModelAnalysis<MODEL, ?> modelAnalysis;

	@Override
	public Future<SyncResult<MODEL, ?>> executeAsync(final MODEL model, final INST instruction, final Object message) {
		modelAnalysis.validate(model);
		return executorService.submit(new Callable<SyncResult<MODEL, ?>>() {

			@Override
			public SyncResult<MODEL, ?> call() throws Exception {
				try {
					return execute(model, instruction, message);
				} catch (Exception e) {
					logger.error("", e);
					throw e;
				}
			}
		});
	}

	@Override
	public SyncResult<MODEL, ?> execute(MODEL model, INST instruction, Object message) {
		modelAnalysis.validate(model);
		AdvancedResult<MODEL, TRIGGER> advancedResult = null;
		while (true) {
			Object status = modelAnalysis.getStatus(model);
			logger.trace("status:{}", status);

			StatusAdvancer<MODEL, INST, TRIGGER> advancer = null;
			if (instruction != null) {
				advancer = advancerMappings.get(Pair.of(status, instruction));
			}
			if (advancer == null) {
				advancer = advancerMappings.get(status);
			}
			logger.trace("advancer:{}", advancer);
			if (advancer == null) {
				return new SyncResult<MODEL, Object>(model);
			}
			advancedResult = advancer.advance(model, instruction, message);
			logger.trace("advancedResult:{}", advancedResult);
			if (advancedResult == null) {
				return new SyncResult<MODEL, Object>(model);
			}
			if (advancedResult.getTriggerBehavior() != null) {
				model = transit(model, advancedResult);
				if (model != null) {
					switch (advancedResult.getNextOperation()) {
					case ADVANCE:
						advancedResult = null;
						continue;
					case ASYNC_ADVANCE:
						logger.debug("executeAsync");
						executeAsync(model, instruction, message);
					case PAUSE:
					}

				}
			}
			break;
		}
		SyncResult<MODEL, ?> syncResult = advancedResult.getSyncResult();
		if (syncResult == null) {
			return new SyncResult<MODEL, Object>(model);
		} else {
			if (syncResult.getModel() == null) {
				syncResult.setModel(model);
			}
			return syncResult;
		}
	}

	/**
	 * 
	 * @param model
	 * @param advancedResult
	 * @return 跃迁是否成功
	 * 
	 * @throws E
	 */
	private MODEL transit(MODEL model, AdvancedResult<MODEL, TRIGGER> advancedResult) {
		if (advancedResult.getTriggerBehavior().getTrigger() == null) {
			logger.trace("transitionSymbol is null");
			return null;
		}
		try {
			TriggerBehavior<TRIGGER, MODEL> triggerBehavior = advancedResult.getTriggerBehavior();
			return txStatusTransitor.transit(model, triggerBehavior);
		} catch (Exception e) {
			throw new ContextedRuntimeException(e);
		}
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setAdvancerMappings(Map<Object, StatusAdvancer<MODEL, INST, TRIGGER>> advancerMappings) {
		this.advancerMappings = advancerMappings;
	}

	public void setTxStatusTransitor(TxStatusTransitor<MODEL, TRIGGER> txStatusTransitor) {
		this.txStatusTransitor = txStatusTransitor;
	}

	public void setModelAnalysis(ModelAnalysis<MODEL, ?> modelAnalysis) {
		this.modelAnalysis = modelAnalysis;
	}

}
