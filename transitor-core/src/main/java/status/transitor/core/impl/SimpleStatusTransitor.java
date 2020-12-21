package status.transitor.core.impl;

import status.transitor.core.intf.StatusTransitor;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SimpleStatusTransitor<STATUS, TRIGGER> implements StatusTransitor<STATUS, TRIGGER> {

    private final Map<STATUS, Map<TRIGGER, STATUS>> config;

    @Override
    public STATUS transit(STATUS status, TRIGGER trigger) {
        Map<TRIGGER, STATUS> triggerBehavior = config.get(status);
        if (triggerBehavior == null) {
            throw new IllegalStateException("status not found:" + status);
        }
        STATUS nextStatus = triggerBehavior.get(trigger);
        if (nextStatus == null) {
            throw new IllegalArgumentException("status:" + status + ", trigger not found:" + trigger);
        }
        return nextStatus;
    }

}
