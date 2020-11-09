package com.godmonth.status.executor.impl;

import com.godmonth.status.executor.intf.Status;
import jodd.bean.BeanUtil;
import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 使用Status注释来寻找status字段
 *
 * @param <MODEL>
 */

public class AnnotationBeanModelAnalysis<MODEL> extends CommonBeanModelAnalysis<MODEL> {

    private List<BeanModelRestriction> beanModelRestrictions;

    @Builder
    public AnnotationBeanModelAnalysis(Class<MODEL> modelClass, List<BeanModelRestriction> beanModelRestrictions) {
        this.modelClass = modelClass;
        this.beanModelRestrictions = beanModelRestrictions;
        init();
    }

    private void init() {
        Field[] fields = FieldUtils.getAllFields(modelClass);
        for (Field field : fields) {
            Status annotation = field.getAnnotation(Status.class);
            if (annotation != null) {
                statusPropertyName = field.getName();
                break;
            }
        }
        Validate.notNull(statusPropertyName, "statusPropertyName is null.");
    }


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


}
