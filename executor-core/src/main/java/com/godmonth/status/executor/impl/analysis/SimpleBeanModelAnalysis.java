package com.godmonth.status.executor.impl.analysis;

import com.godmonth.status.executor.intf.ModelAnalysis;
import jodd.bean.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

/**
 * @param <MODEL>
 */
public class SimpleBeanModelAnalysis<MODEL> implements ModelAnalysis<MODEL> {
    @Setter
    protected Class<MODEL> modelClass;

    @Setter
    @Getter
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
