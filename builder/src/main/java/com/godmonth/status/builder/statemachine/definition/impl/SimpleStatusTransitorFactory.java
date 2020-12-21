package com.godmonth.status.builder.statemachine.definition.impl;

import com.godmonth.status.builder.statemachine.definition.intf.StatusDefinition;
import status.transitor.core.impl.SimpleStatusTransitor;

public class SimpleStatusTransitorFactory {

	public static <STATUS, TRIGGER> SimpleStatusTransitor<STATUS, TRIGGER> build(String json,
			Class<? extends StatusDefinition<STATUS, TRIGGER>> statusDefinitionClass) {
		return new SimpleStatusTransitor<STATUS, TRIGGER>(JsonDefinition.parse(json, statusDefinitionClass));
	}
}
