package com.godmonth.status.executor.impl.analysis;

import lombok.Setter;

import java.util.List;

/**
 * @param <MODEL>
 * @see AnnotationBeanModelAnalysis
 */

public class BeanModelAnalysis2<MODEL> extends SimpleBeanModelAnalysis<MODEL> {

    @Setter
    private List<BeanModelRestriction> beanModelRestrictions;

    @Override
    public void validate(MODEL model) {
        super.validate(model);
        BeanModelRestrictions.check(beanModelRestrictions, model);
    }


}
