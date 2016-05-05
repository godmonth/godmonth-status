package com.godmonth.commons.status.sample;

import java.util.Map;

public class StatusMachine<STATUS, TRIGGER> {
	private Map<STATUS, Map<TRIGGER, STATUS>> definition;
}
