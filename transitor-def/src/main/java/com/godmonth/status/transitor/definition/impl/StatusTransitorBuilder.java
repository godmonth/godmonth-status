package com.godmonth.status.transitor.definition.impl;

import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.core.intf.StatusTransitor;

public class StatusTransitorBuilder<STATUS, TRIGGER> extends JsonDefinitionBuilder<STATUS, TRIGGER> {
	
	public StatusTransitor<STATUS, TRIGGER> buildTransitor() {
		return new SimpleStatusTransitor<STATUS, TRIGGER>(build());
	}
	
}
