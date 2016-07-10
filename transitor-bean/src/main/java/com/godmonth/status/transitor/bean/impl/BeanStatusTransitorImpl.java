package com.godmonth.status.transitor.bean.impl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godmonth.status.transitor.bean.intf.BeanStatusTransitor;
import com.godmonth.status.transitor.bean.intf.StatusEntry;
import com.godmonth.status.transitor.bean.intf.StatusExit;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.google.common.util.concurrent.MoreExecutors;

import jodd.bean.BeanUtil;

public class BeanStatusTransitorImpl<MODEL, STATUS, TRIGGER> implements BeanStatusTransitor<MODEL, TRIGGER> {

	private static final Logger logger = LoggerFactory.getLogger(BeanStatusTransitorImpl.class);

	private StatusTransitor<STATUS, TRIGGER> statusTransitor;

	protected String statusPropertyName;

	private Map<STATUS, StatusExit<MODEL>> statusExitMap = Collections.emptyMap();

	private Map<STATUS, StatusEntry<MODEL>> statusEntryMap = Collections.emptyMap();

	private Executor executorService = MoreExecutors.directExecutor();

	@Override
	public MODEL transit(MODEL model, TRIGGER trigger) {
		STATUS nextStatus = beforeChange(model, trigger);

		MODEL mergedModel = mergeModel(model, nextStatus);

		afterChange(mergedModel, nextStatus);

		return mergedModel;
	}

	protected MODEL mergeModel(MODEL model, STATUS nextStatus) {
		BeanUtil.silent.setProperty(model, statusPropertyName, nextStatus);
		return model;
	}

	protected STATUS beforeChange(MODEL model, TRIGGER trigger) {
		STATUS status = BeanUtil.silent.getProperty(model, statusPropertyName);
		Validate.notNull(status, "status is null");

		STATUS nextStatus = statusTransitor.transit(status, trigger);

		if (statusExitMap.get(status) != null) {
			statusExitMap.get(status).previousStatusExit(model);
		}
		return nextStatus;
	}

	protected void afterChange(MODEL model, STATUS status) {
		if (statusEntryMap.get(status) != null) {
			executorService.execute(() -> {
				try {
					statusEntryMap.get(status).nextStatusEntry(model);
				} catch (Exception e) {
					logger.error("", e);
					throw e;
				}
			});
		}
	}

	public void setStatusTransitor(StatusTransitor<STATUS, TRIGGER> statusTransitor) {
		this.statusTransitor = statusTransitor;
	}

	public void setStatusExitMap(Map<STATUS, StatusExit<MODEL>> statusExitMap) {
		this.statusExitMap = statusExitMap;
	}

	public void setStatusEntryMap(Map<STATUS, StatusEntry<MODEL>> statusEntryMap) {
		this.statusEntryMap = statusEntryMap;
	}

	public void setStatusPropertyName(String statusPropertyName) {
		this.statusPropertyName = statusPropertyName;
	}

}
