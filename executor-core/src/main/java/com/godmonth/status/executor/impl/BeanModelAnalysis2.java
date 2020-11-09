package com.godmonth.status.executor.impl;

import jodd.bean.BeanUtil;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * @param <MODEL>
 * @see AnnotationBeanModelAnalysis
 */
@SuppressWarnings("rawtypes")
public class BeanModelAnalysis2<MODEL> extends AbstractBeanModelAnalysis<MODEL> {

    @Setter
    private List<BeanModelRestriction> beanModelRestrictions;

    @Override
    public void validate(MODEL model) {
        super.validate(model);
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


}
