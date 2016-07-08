package com.godmonth.commons.status.impl.transitor.definition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.godmonth.commons.status.intf.StatusConfig;

public class JsonDefinition {

	public <T, S> List<StatusConfig<S, T>> parse(String json,
			Class<? extends StatusDefinition<S, T>> statusDefinitionClass)
			throws JsonParseException, JsonMappingException, IOException {
		TypeFactory typeFactory = TypeFactory.defaultInstance();

		ObjectMapper objectMapper = new ObjectMapper();

		CollectionType collectionType = typeFactory.constructCollectionType(List.class, statusDefinitionClass);
		List<StatusDefinition<S, T>> statusDefinitions = objectMapper.readValue(json, collectionType);
		List<StatusConfig<S, T>> statusConfigs = new ArrayList<>();
		for (StatusDefinition<S, T> statusDefinition : statusDefinitions) {
			for (TriggerDefinition<T, S> triggerDefinition : statusDefinition.getTriggerDefinitions()) {
				StatusConfig<S, T> statusConfig = new StatusConfig<>();
				statusConfig.setStatus(statusDefinition.getStatus());
				statusConfig.setTrigger(triggerDefinition.getTrigger());
				statusConfig.setNextStatus(triggerDefinition.getNextStatus());
				statusConfigs.add(statusConfig);
			}
		}
		return statusConfigs;
	}
}
