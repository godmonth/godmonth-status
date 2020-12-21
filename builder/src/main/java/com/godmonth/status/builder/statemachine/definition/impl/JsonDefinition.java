package com.godmonth.status.builder.statemachine.definition.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.godmonth.status.builder.statemachine.definition.intf.StatusDefinition;
import com.godmonth.status.builder.statemachine.definition.intf.TriggerDefinition;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonDefinition {

	private static final Logger logger = LoggerFactory.getLogger(JsonDefinition.class);

	public static <STATUS, TRIGGER> Map<STATUS, Map<TRIGGER, STATUS>> parse(String json,
			Class<? extends StatusDefinition<STATUS, TRIGGER>> definitionClass) {
		logger.trace("json:{}", json);

		TypeFactory typeFactory = TypeFactory.defaultInstance();

		ObjectMapper objectMapper = new ObjectMapper();

		CollectionType collectionType = typeFactory.constructCollectionType(List.class, definitionClass);
		List<StatusDefinition<STATUS, TRIGGER>> statusDefinitions;
		try {
			statusDefinitions = objectMapper.readValue(json, collectionType);
		} catch (IOException e) {
			throw new ContextedRuntimeException(e);
		}
		Map<STATUS, Map<TRIGGER, STATUS>> statusConfigs = new HashMap<>();

		for (StatusDefinition<STATUS, TRIGGER> statusDefinition : statusDefinitions) {
			Validate.notNull(statusDefinition.getStatus(), "status is null");
			Validate.notNull(statusDefinition.getTriggers(), "triggerDefinitions is null");
			Map<TRIGGER, STATUS> triggerConfig = new HashMap<>();
			for (TriggerDefinition<TRIGGER, STATUS> triggerDefinition : statusDefinition.getTriggers()) {
				Validate.notNull(triggerDefinition.getTrigger(), "trigger is null");
				Validate.notNull(triggerDefinition.getNextStatus(), "nextStatus is null");
				triggerConfig.put(triggerDefinition.getTrigger(), triggerDefinition.getNextStatus());
			}
			statusConfigs.put(statusDefinition.getStatus(), triggerConfig);
		}
		return statusConfigs;
	}
}
