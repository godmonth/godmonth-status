package com.godmonth.status.executor.impl;

import lombok.Setter;

import java.util.List;

/**
 * @param <MODEL>
 * @see AnnotationBeanModelAnalysis
 */

public class BeanModelAnalysis2<MODEL> extends AbstractBeanModelAnalysis<MODEL> {

    @Setter
    private List<BeanModelRestriction> beanModelRestrictions;

    @Override
    public void validate(MODEL model) {
        super.validate(model);
        BeanModelRestrictions.check(beanModelRestrictions, model);
    }


    public void setStatusPropertyName(String statusPropertyName) {
        this.statusPropertyName = statusPropertyName;
    }

    public void setModelClass(Class<MODEL> modelClass) {
        this.modelClass = modelClass;
    }


}
