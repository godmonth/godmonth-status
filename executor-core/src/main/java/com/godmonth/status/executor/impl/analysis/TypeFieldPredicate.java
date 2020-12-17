package com.godmonth.status.executor.impl.analysis;

import jodd.bean.BeanUtil;
import lombok.Setter;

import java.util.function.Predicate;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class TypeFieldPredicate<MODEL, VALUE> implements Predicate<MODEL> {
    @Setter
    private VALUE expectedTypeValue;

    @Setter
    private String typePropertyName;

    @Override
    public boolean test(MODEL model) {
        VALUE actualValue = BeanUtil.silent.getProperty(model, typePropertyName);
        return expectedTypeValue.equals(actualValue);
    }
}
