package com.godmonth.status.executor.impl.analysis;

import com.godmonth.status.annotations.Status;
import lombok.Builder;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

/**
 * 使用Status注释来寻找status字段
 *
 * @param <MODEL>
 */

public class AnnotationBeanModelAnalysis<MODEL> extends SimpleBeanModelAnalysis<MODEL> {


    @Builder
    public AnnotationBeanModelAnalysis(Class<MODEL> modelClass, List<Predicate<MODEL>> predicateList) {
        this.modelClass = modelClass;
        this.predicateList = predicateList;
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

}
