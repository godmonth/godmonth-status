package com.godmonth.status.executor.impl;

import com.godmonth.status.executor.intf.ModelAnalysis;
import jodd.bean.BeanUtil;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @param <MODEL>
 * @param <VALUE>
 * @see AnnotationBeanModelAnalysis
 */
@Deprecated
public class BeanModelAnalysis<MODEL, VALUE> implements ModelAnalysis<MODEL> {

    @Setter
    private Class<MODEL> modelClass;

    @Setter
    private VALUE expectedTypeValue;

    @Setter
    private String typePropertyName;

    @Setter
    private String statusPropertyName;

    @Override
    public void validate(MODEL model) {
        if (modelClass != null) {
            Validate.isTrue(modelClass.equals(model.getClass()));
        }
        if (StringUtils.isNotBlank(typePropertyName) && expectedTypeValue != null) {
            VALUE actualValue = BeanUtil.silent.getProperty(model, typePropertyName);
            Validate.isTrue(expectedTypeValue.equals(actualValue), "expected:%s,actual:%s", expectedTypeValue,
                    actualValue);
        }
    }

    @Override
    public <STATUS> STATUS getStatus(MODEL model) {
        return BeanUtil.silent.getProperty(model, statusPropertyName);
    }


}
