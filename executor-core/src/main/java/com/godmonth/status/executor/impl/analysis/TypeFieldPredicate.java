package com.godmonth.status.executor.impl.analysis;

import jodd.bean.BeanUtil;
import lombok.Builder;
import lombok.Setter;

import java.util.function.Predicate;

/**
 * <p></p >
 *
 * @author shenyue
 */
@Builder
public class TypeFieldPredicate implements Predicate {
    @Setter
    private Object expectedTypeValue;

    @Setter
    private String typePropertyName;

    @Override
    public boolean test(Object model) {
        Object actualValue = BeanUtil.silent.getProperty(model, typePropertyName);
        return expectedTypeValue.equals(actualValue);
    }
}
