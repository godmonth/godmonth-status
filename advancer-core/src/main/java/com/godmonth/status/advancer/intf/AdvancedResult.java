package com.godmonth.status.advancer.intf;

import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 推进结果.当跃迁参数为空时,查看同步结果
 *
 * @param <MODEL>
 * @author shenyue
 */
@Builder
@Data
@AllArgsConstructor
public class AdvancedResult<MODEL, TRIGGER> {
    /**
     * 推进的同步结果
     */
    public SyncData syncData;

    /**
     * 跃迁参数
     */
    private TriggerBehavior<TRIGGER, MODEL> triggerBehavior;
    /**
     * 跃迁完成后动作
     */
    @Builder.Default
    private NextOperation nextOperation = NextOperation.ADVANCE;
    /**
     * 丢弃指令
     */
    private boolean dropInstruction;

    /**
     * @param triggerBehavior
     */

    public AdvancedResult(TriggerBehavior<TRIGGER, MODEL> triggerBehavior) {
        this.triggerBehavior = triggerBehavior;
    }

    /**
     * @param triggerBehavior
     * @param nextOperation
     */
    public AdvancedResult(TriggerBehavior<TRIGGER, MODEL> triggerBehavior, NextOperation nextOperation) {
        this.triggerBehavior = triggerBehavior;
        this.nextOperation = nextOperation;
    }

    /**
     * @param triggerBehavior
     * @param nextOperation
     * @param dropInstruction
     */
    public AdvancedResult(TriggerBehavior<TRIGGER, MODEL> triggerBehavior, NextOperation nextOperation,
                          boolean dropInstruction) {
        this.triggerBehavior = triggerBehavior;
        this.nextOperation = nextOperation;
        this.dropInstruction = dropInstruction;
    }

    /**
     * use setSyncData
     *
     * @param syncResult
     */
    @Deprecated
    public void setSyncResult(SyncResult syncResult) {
        if (syncResult != null) {
            syncData = SyncData.builder().symbol(syncResult.getSymbol()).value(syncResult.getValue()).build();
        } else {
            syncData = null;
        }
    }

}
