package com.godmonth.status.executor.impl;

import com.godmonth.status.executor.intf.ModelAnalysis;
import jodd.bean.BeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * @param <MODEL>
 * @see AnnotationBeanModelAnalysis
 */
@SuppressWarnings("rawtypes")
public class BeanModelAnalysis2<MODEL> implements ModelAnalysis<MODEL> {

    private Class<MODEL> modelClass;

    private String statusPropertyName;

    private List<BeanModelRestriction> beanModelRestrictions;

    @Override
    public void validate(MODEL model) {
        if (modelClass != null) {
            Validate.isTrue(modelClass.equals(model.getClass()));
        }

        if (CollectionUtils.isNotEmpty(beanModelRestrictions)) {
            for (BeanModelRestriction beanModelRestriction : beanModelRestrictions) {
                Object actualValue = BeanUtil.silent.getProperty(model, beanModelRestriction.getRestrictionProperty());
                Validate.isTrue(beanModelRestriction.getRestrictionValue().equals(actualValue), "expected:%s,actual:%s",
                        beanModelRestriction.getRestrictionValue(), actualValue);
            }
        }

    }

    @Override
    public <STATUS> STATUS getStatus(MODEL model) {
        return BeanUtil.silent.getProperty(model, statusPropertyName);
    }

    public void setStatusPropertyName(String statusPropertyName) {
        this.statusPropertyName = statusPropertyName;
    }

    public void setModelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }

    public void setBeanModelRestrictions(List<BeanModelRestriction> beanModelRestrictions) {
        this.beanModelRestrictions = beanModelRestrictions;
    }

}
