package com.godmonth.status.transitor.core.intf;

/**
 * 状态跃迁器
 * 
 * @author shenyue
 *
 * @param <TRIGGER>
 * @param <STATUS>
 * @param <E>
 */
public interface StatusTransitor<STATUS, TRIGGER> {

	STATUS transit(STATUS status, TRIGGER trigger);

}
