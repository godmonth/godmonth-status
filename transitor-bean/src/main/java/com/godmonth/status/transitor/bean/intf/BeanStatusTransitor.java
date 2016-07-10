package com.godmonth.status.transitor.bean.intf;

/**
 * 状态跃迁器
 * 
 * @author shenyue
 *
 * @param <MODEL>
 * @param <TRIGGER>
 */
public interface BeanStatusTransitor<MODEL, TRIGGER> {

	MODEL transit(MODEL model, TRIGGER trigger);

}
