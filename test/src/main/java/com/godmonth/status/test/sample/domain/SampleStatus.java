package com.godmonth.status.test.sample.domain;

import com.godmonth.status.annotations.Trigger;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;

/**
 * 测试状态
 *
 * @author shenyue
 */
@Trigger(SampleTrigger.class)
public enum SampleStatus {
    /**
     * 已创建
     */
    CREATED("已创建"),

    /**
     * 已付款
     */
    PAID("已付款");

    private String description;

    private SampleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
