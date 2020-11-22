package com.godmonth.status.executor.intf;

/**
 * 升级，去掉status泛型
 *
 * @param <MODEL>
 */
public interface ModelAnalysis<MODEL> {
    void validate(MODEL model);

    <STATUS> STATUS getStatus(MODEL model);

    String  getStatusPropertyName();
}
