package com.godmonth.status.transitor.tx.impl;

import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TransitedResult;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionOperations;

import java.util.function.Function;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxStatusTransitorImpl<MODEL, STATUS, TRIGGER>
        implements TxStatusTransitor<MODEL, TRIGGER> {

    @Setter
    protected ModelAnalysis<MODEL> modelAnalysis;

    @Setter
    private TransactionOperations transactionOperations;

    @Setter
    private StatusTransitor<STATUS, TRIGGER> statusTransitor;

    @Setter
    private Function<STATUS, StatusEntry<MODEL, Object>> statusFunction;

    @Setter
    private Merger<MODEL> modelMerger;

    @Override
    public MODEL transit(MODEL model, TriggerBehavior<TRIGGER, MODEL> triggerBehavior) {
        STATUS nextStatus = beforeChange(model, triggerBehavior.getTrigger());
        TransitedResult<MODEL, Object> transitedResult = transactionOperations.execute((TransactionStatus status) -> {
            modelAnalysis.setStatus(model, nextStatus);
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
        STATUS status = modelAnalysis.getStatus(model);
        Validate.notNull(status, "status is null");

        STATUS nextStatus = statusTransitor.transit(status, trigger);
        Validate.notNull(nextStatus, "nextStatus is null");

        return nextStatus;
    }

    protected void afterChange(TransitedResult<MODEL, Object> transitedResult) {
        STATUS status = modelAnalysis.getStatus(transitedResult.getModel());
        Validate.notNull(status, "status is null");
        if (statusFunction != null) {
            StatusEntry<MODEL, Object> statusEntry = statusFunction.apply(status);
            if (statusEntry != null) {
                statusEntry.nextStatusEntry(transitedResult);
            }
        }
    }
}
