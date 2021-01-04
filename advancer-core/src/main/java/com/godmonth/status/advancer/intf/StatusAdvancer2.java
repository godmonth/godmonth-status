package com.godmonth.status.advancer.intf;

/**
 * 状态推进器.不再关联key,使用 {@link com.godmonth.status.annotations.ModelBinding} 和 {{@link AdvancerBinding}} 关联.
 *
 * @param <MODEL> MODEL
 * @author shenyue
 */
public interface StatusAdvancer2<MODEL, INST, TRIGGER> {
    AdvancedResult<MODEL, TRIGGER> advance(AdvanceRequest<MODEL, INST> advanceRequest);
}
