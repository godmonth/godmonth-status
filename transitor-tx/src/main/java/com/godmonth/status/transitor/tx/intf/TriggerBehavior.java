package com.godmonth.status.transitor.tx.intf;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 跃迁参数
 * 
 */
public class TriggerBehavior<TRIGGER, MODEL> {

	private TRIGGER trigger;

	private TransitionCallback<MODEL> transitionCallback;
	private boolean lock;

	public TriggerBehavior() {
	}

	public TriggerBehavior(TRIGGER trigger) {
		this.trigger = trigger;
	}

	public TriggerBehavior(TRIGGER trigger, TransitionCallback<MODEL> transitionCallback) {
		this.trigger = trigger;
		this.transitionCallback = transitionCallback;
	}

	public TriggerBehavior(TRIGGER trigger, TransitionCallback<MODEL> transitionCallback, boolean lock) {
		this.trigger = trigger;
		this.transitionCallback = transitionCallback;
		this.lock = lock;
	}

	public TRIGGER getTrigger() {
		return trigger;
	}

	public void setTrigger(TRIGGER trigger) {
		this.trigger = trigger;
	}

	public TransitionCallback<MODEL> getTransitionCallback() {
		return transitionCallback;
	}

	public void setTransitionCallback(TransitionCallback<MODEL> transitionCallback) {
		this.transitionCallback = transitionCallback;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("transitionCallback", this.transitionCallback).append("trigger", this.trigger).toString();
	}

}
