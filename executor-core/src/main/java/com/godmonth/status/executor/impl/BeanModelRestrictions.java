package com.godmonth.status.executor.impl;

import jodd.bean.BeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class BeanModelRestrictions {
    private BeanModelRestrictions() {
    }

    public static void check(List<BeanModelRestriction> beanModelRestrictions, Object model) {
        if (CollectionUtils.isNotEmpty(beanModelRestrictions)) {
            for (BeanModelRestriction beanModelRestriction : beanModelRestrictions) {
                Object actualValue = BeanUtil.silent.getProperty(model, beanModelRestriction.getRestrictionProperty());
                Validate.isTrue(beanModelRestriction.getRestrictionValue().equals(actualValue), "expected:%s,actual:%s",
                        beanModelRestriction.getRestrictionValue(), actualValue);
            }
        }
    }
}
