package com.godmonth.status.advancer.intf;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.godmonth.status.transitor.tx.intf.TriggerBehavior;

/**
 * 推进结果.当跃迁参数为空时,查看同步结果
 * 
 * @author shenyue
 * @param <MODEL>
 */
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

	public AdvancedResult(TriggerBehavior<TRIGGER, MODEL> triggerBehavior) {
		this.triggerBehavior = triggerBehavior;
	}

	public AdvancedResult(TriggerBehavior<TRIGGER, MODEL> triggerBehavior, NextOperation nextOperation) {
		this.triggerBehavior = triggerBehavior;
		this.nextOperation = nextOperation;
	}

	public AdvancedResult(SyncResult<MODEL, ?> syncResult) {
		this.syncResult = syncResult;
		this.nextOperation = NextOperation.PAUSE;
	}

	public TriggerBehavior<TRIGGER, MODEL> getTriggerBehavior() {
		return triggerBehavior;
	}

	public void setTriggerBehavior(TriggerBehavior<TRIGGER, MODEL> triggerBehavior) {
		this.triggerBehavior = triggerBehavior;
	}

	public SyncResult<MODEL, ?> getSyncResult() {
		return syncResult;
	}

	public void setSyncResult(SyncResult<MODEL, ?> syncResult) {
		this.syncResult = syncResult;
	}

	public NextOperation getNextOperation() {
		return nextOperation;
	}

	public void setNextOperation(NextOperation nextOperation) {
		this.nextOperation = nextOperation;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("transitionParam", this.triggerBehavior)
				.append("syncResult", this.syncResult).append("nextOperation", this.nextOperation).toString();
	}

}