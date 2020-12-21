package com.godmonth.status.builder.statemachine.definition.impl;

import status.transitor.core.impl.SimpleStatusTransitor;
import status.transitor.core.intf.StatusTransitor;

public class StatusTransitorBuilder<STATUS, TRIGGER> extends JsonDefinitionBuilder<STATUS, TRIGGER> {
	
	public StatusTransitor<STATUS, TRIGGER> buildTransitor() {
		return new SimpleStatusTransitor<STATUS, TRIGGER>(build());
	}
	
}
