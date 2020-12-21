package com.godmonth.status.advancer.intf;

import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 推进结果.当跃迁参数为空时,查看同步结果
 *
 * @param <MODEL>
 * @author shenyue
 */
@Data
@AllArgsConstructor
public class AdvancedResult<MODEL, TRIGGER> {
    /**
     * 跃迁参数
     */
    private TriggerBehavior<TRIGGER, MODEL> triggerBehavior;
    /**
     * 同步结果
     */
    private SyncResult<MODEL, ?> syncResult;
    /**
     * 跃迁完成后动作
     */
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
     * @param syncResult
     * @deprecated use builder
     */
    public AdvancedResult(SyncResult<MODEL, ?> syncResult) {
        this.syncResult = syncResult;
        this.nextOperation = NextOperation.PAUSE;
    }

}
