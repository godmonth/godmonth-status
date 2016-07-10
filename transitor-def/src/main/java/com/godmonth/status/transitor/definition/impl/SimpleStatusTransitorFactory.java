package com.godmonth.status.transitor.definition.impl;

import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.definition.intf.StatusDefinition;

public class SimpleStatusTransitorFactory {

	public static <STATUS, TRIGGER> SimpleStatusTransitor<STATUS, TRIGGER> build(String json,
			Class<? extends StatusDefinition<STATUS, TRIGGER>> statusDefinitionClass) {
		return new SimpleStatusTransitor<STATUS, TRIGGER>(JsonDefinition.parse(json, statusDefinitionClass));
	}
}
