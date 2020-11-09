package com.godmonth.status.executor.impl;

import com.godmonth.status.executor.intf.ModelAnalysis;
import jodd.bean.BeanUtil;
import org.apache.commons.lang3.Validate;

/**
 * @param <MODEL>
 */
public abstract class CommonBeanModelAnalysis<MODEL> implements ModelAnalysis<MODEL> {

    protected Class<MODEL> modelClass;

    protected String statusPropertyName;

    @Override
    public void validate(MODEL model) {
        Validate.isTrue(modelClass.equals(model.getClass()), "modeClass mismatched,expected:%s,actual:%s", modelClass, model.getClass());
    }

    @Override
    public <STATUS> STATUS getStatus(MODEL model) {
        return BeanUtil.silent.getProperty(model, statusPropertyName);
    }

}
