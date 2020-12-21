package com.godmonth.status.builder.statemachine.definition.intf;

import lombok.Data;

import java.util.List;

/**
 * 状态定义
 *
 * @param <STATUS>
 * @param <TRIGGER>
 * @author shenyue
 */
@Data
public class StatusDefinition<STATUS, TRIGGER> {

	private STATUS status;

    private List<TriggerDefinition<TRIGGER, STATUS>> triggers;

}
