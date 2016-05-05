package com.godmonth.commons.status.impl.transitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godmonth.commons.status.intf.TransitionCallback;
import com.godmonth.commons.status.intf.TriggerBehavior;

import jodd.bean.BeanUtil;

public abstract class AbstractStatusTransitor<TRIGGER, STATUS, MODEL, E extends Exception>
		implements StatusTransitor<TRIGGER, MODEL, E> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractStatusTransitor.class);

	private Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>> transitionConfigMap = new HashMap<>();

	private String statusPropertyName;

	private ExecutorService executorService;

	@Override
	public MODEL transit(MODEL model, TriggerBehavior<TRIGGER, MODEL> triggerBehavior) throws E {
		Object status = BeanUtil.silent.getProperty(model, statusPropertyName);
		Validate.notNull(status, "status is null");
		TriggerKey<?, TRIGGER> tk = new TriggerKey<>(status, triggerBehavior.getTrigger());
		final TransitionConfig<?, TRIGGER, MODEL, E> transitionConfig = transitionConfigMap.get(tk);
		if (transitionConfig == null) {
			throw new IllegalArgumentException("transitionConfig not found." + tk);
		}
		if (transitionConfig.getStatusExit() != null) {
			transitionConfig.getStatusExit().previousStatusExit(model);
		}
		changeStatus(model, statusPropertyName, transitionConfig.getStatusConfig().getStatus(),
				transitionConfig.getStatusConfig().getNextStatus());
		MODEL mergedModel = mergeModel(model, triggerBehavior.getTransitionCallback());

		if (transitionConfig.getStatusEntry() != null) {
			if (executorService != null) {
				executorService.submit(() -> {
					try {
						transitionConfig.getStatusEntry().nextStatusEntry(mergedModel);
						return null;
					} catch (Exception e) {
						logger.error("", e);
						throw e;
					}
				});
			} else {
				transitionConfig.getStatusEntry().nextStatusEntry(mergedModel);
			}
		}
		return mergedModel;

	}

	private static <T> void changeStatus(final Object model, String propertyName, T previousStatus, T nextStatus) {
		Object property = BeanUtil.silent.getProperty(model, propertyName);
		Validate.validState(previousStatus.equals(property), "expected status:%s, actual status:%s", previousStatus,
				property);
		BeanUtil.silent.setProperty(model, propertyName, nextStatus);
	}

	protected abstract MODEL mergeModel(MODEL model, TransitionCallback<MODEL> transitionCallback) throws E;

	public void setTransitionConfigMap(
			Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>> transitionConfigMap) {
		this.transitionConfigMap = transitionConfigMap;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setStatusPropertyName(String statusPropertyName) {
		this.statusPropertyName = statusPropertyName;
	}

}
