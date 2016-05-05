package com.godmonth.commons.status.impl.transitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.godmonth.commons.status.intf.StatusConfig;
import com.godmonth.commons.status.intf.StatusEntry;
import com.godmonth.commons.status.intf.StatusExit;

public class TransitionConfigMapFactory {

	private static <STATUS, TRIGGER, MODEL, E extends Exception> Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>> createByStatusConfigList(
			List<StatusConfig<STATUS, TRIGGER>> statusConfigList, Map<STATUS, StatusEntry<MODEL, E>> statusEntryMap,
			Map<STATUS, StatusExit<MODEL, E>> statusExitMap) {

		Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>> triggerMap = new HashMap<>();

		for (StatusConfig<STATUS, TRIGGER> statusConfig : statusConfigList) {
			TransitionConfig<STATUS, TRIGGER, MODEL, E> transitionConfig = new TransitionConfig<>();
			transitionConfig.setStatusConfig(statusConfig);

			if (statusEntryMap != null) {
				StatusEntry<MODEL, E> statusEntry = statusEntryMap.get(statusConfig.getNextStatus());
				if (statusEntry != null) {
					transitionConfig.setStatusEntry(statusEntry);
				}
			}

			if (statusExitMap != null) {
				StatusExit<MODEL, E> statusExit = statusExitMap.get(statusConfig.getStatus());
				if (statusExit != null) {
					transitionConfig.setStatusExit(statusExit);
				}
			}
			triggerMap.put(statusConfig.getTriggerKey(), transitionConfig);
		}
		return triggerMap;
	}

	public static <STATUS, TRIGGER, MODEL, E extends Exception> Map<TriggerKey<STATUS, TRIGGER>, TransitionConfig<STATUS, TRIGGER, MODEL, E>> createByStatusMachine(
			Map<STATUS, Map<TRIGGER, STATUS>> statusMachine, Map<STATUS, StatusEntry<MODEL, E>> statusEntryMap,
			Map<STATUS, StatusExit<MODEL, E>> statusExitMap) {
		List<StatusConfig<STATUS, TRIGGER>> statusConfigList = new ArrayList<>();
		for (STATUS status : statusMachine.keySet()) {
			Map<TRIGGER, STATUS> triggerOperation = statusMachine.get(status);
			for (TRIGGER trigger : triggerOperation.keySet()) {
				STATUS nextStatus = triggerOperation.get(trigger);
				if (nextStatus != null) {
					statusConfigList.add(new StatusConfig<STATUS, TRIGGER>(status, trigger, nextStatus));
				}
			}
		}
		return createByStatusConfigList(statusConfigList, statusEntryMap, statusExitMap);
	}
}
