package com.godmonth.status.transitor.tx.impl;

import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TransitedResult;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import jodd.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxStatusTransitorImpl<MODEL, STATUS, TRIGGER>
        implements TxStatusTransitor<MODEL, TRIGGER> {

    @Setter
    protected String statusPropertyName;

    @Setter
    private TransactionOperations transactionOperations;

    @Setter
    private StatusTransitor<STATUS, TRIGGER> statusTransitor;

    @Setter
    private Map<STATUS, StatusEntry<MODEL, Object>> statusEntryMap = Collections.emptyMap();

    @Setter
    private Merger<MODEL> modelMerger;

    @Override
    public MODEL transit(MODEL model, TriggerBehavior<TRIGGER, MODEL> triggerBehavior) {
        STATUS nextStatus = beforeChange(model, triggerBehavior.getTrigger());
        TransitedResult<MODEL, Object> transitedResult = transactionOperations.execute((TransactionStatus status) -> {
            BeanUtil.silent.setProperty(model, statusPropertyName, nextStatus);
            Object accessory = null;
            if (triggerBehavior.getTransitionCallback() != null) {
                accessory = triggerBehavior.getTransitionCallback().beforeMerge(model);
            }
            MODEL mergedModelInTx = modelMerger.mergeInTx(model);
            return new TransitedResult(mergedModelInTx, accessory);
        });
        afterChange(transitedResult);

        return transitedResult.getModel();
    }

    protected STATUS beforeChange(MODEL model, TRIGGER trigger) {
        STATUS status = BeanUtil.silent.getProperty(model, statusPropertyName);
        Validate.notNull(status, "status is null");

        STATUS nextStatus = statusTransitor.transit(status, trigger);
        Validate.notNull(nextStatus, "nextStatus is null");

        return nextStatus;
    }

    protected void afterChange(TransitedResult<MODEL, Object> transitedResult) {
        STATUS status = BeanUtil.silent.getProperty(transitedResult.getModel(), statusPropertyName);
        Validate.notNull(status, "status is null");
        if (statusEntryMap.get(status) != null) {
            statusEntryMap.get(status).nextStatusEntry(transitedResult);
        }
    }
}
