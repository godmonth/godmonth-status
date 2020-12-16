package com.godmonth.status.executor.impl.analysis;

import jodd.bean.BeanUtil;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @param <MODEL>
 * @param <VALUE>
 * @see AnnotationBeanModelAnalysis
 */
public class BeanModelAnalysis<MODEL, VALUE> extends SimpleBeanModelAnalysis<MODEL> {

    @Setter
    private VALUE expectedTypeValue;

    @Setter
    private String typePropertyName;

    @Override
    public void validate(MODEL model) {
        super.validate(model);
        if (StringUtils.isNotBlank(typePropertyName) && expectedTypeValue != null) {
            VALUE actualValue = BeanUtil.silent.getProperty(model, typePropertyName);
            Validate.isTrue(expectedTypeValue.equals(actualValue), "expected:%s,actual:%s", expectedTypeValue,
                    actualValue);
        }
    }

}
