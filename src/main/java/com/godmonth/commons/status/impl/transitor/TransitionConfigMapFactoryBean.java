package com.godmonth.commons.status.impl.transitor;

import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

import com.godmonth.commons.status.intf.StatusEntry;
import com.godmonth.commons.status.intf.StatusExit;

public class TransitionConfigMapFactoryBean<STATUS, TRIGGER, MODEL, E extends Exception>
		implements FactoryBean<Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>>> {
	private Map<STATUS, Map<TRIGGER, STATUS>> statusMachine;
	private Map<STATUS, StatusEntry<MODEL, E>> statusEntryMap;
	private Map<STATUS, StatusExit<MODEL, E>> statusExitMap;

	public void setStatusMachine(Map<STATUS, Map<TRIGGER, STATUS>> statusMachine) {
		this.statusMachine = statusMachine;
	}

	public void setStatusEntryMap(Map<STATUS, StatusEntry<MODEL, E>> statusEntryMap) {
		this.statusEntryMap = statusEntryMap;
	}

	public void setStatusExitMap(Map<STATUS, StatusExit<MODEL, E>> statusExitMap) {
		this.statusExitMap = statusExitMap;
	}

	@Override
	public Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>> getObject() throws Exception {
		return TransitionConfigMapFactory.createByStatusMachine(statusMachine, statusEntryMap, statusExitMap);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
