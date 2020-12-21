package com.godmonth.status.builder.statemachine.definition.intf;

import lombok.Data;

/**
 * @param <TRIGGER>
 * @param <STATUS>
 * @author shenyue
 */
@Data
public class TriggerDefinition<TRIGGER, STATUS> {

    private TRIGGER trigger;

    private STATUS nextStatus;

}
