package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.AdvancerBinding;
import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.annotations.AdvancerBindingAnnotation;
import com.godmonth.status.annotations.InstructionBindingAnnotation;
import com.godmonth.status.annotations.ModelBinding;
import com.godmonth.status.annotations.utils.AdvancerBindingAnnotationUtils;
import com.godmonth.status.annotations.utils.InstructionBindingAnnotationUtils;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * <p></p >
 *
 * @author shenyue
 */
@Slf4j
public class AdvancerBindingListBuilder {

    @Builder
    private static List<AdvancerBinding> build(@Singular Set<String> packageNames, Class modelClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory) throws IOException, ClassNotFoundException {
        Validate.notEmpty(packageNames, "packageNames is empty");
        List<AdvancerBinding> list = new ArrayList<>();
        ClassPath from = ClassPath.from(ClassLoader.getSystemClassLoader());
        for (String packageName : packageNames) {
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = from.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
                Class<?> aClass = Class.forName(topLevelClass.getName());
                ModelBinding annotation = AnnotationUtils.findAnnotation(aClass, ModelBinding.class);
                if (annotation != null && modelClass.equals(annotation.value())) {
                    if (predicate != null && !predicate.test(aClass)) {
                        continue;
                    }
                    list.add(createByAnnotation(aClass, autowireCapableBeanFactory));
                }
            }

        }
        log.debug("advancerList:{}", list);
        return list;
    }

    public static AdvancerBinding createByAnnotation(Class aClass, AutowireCapableBeanFactory autowireCapableBeanFactory) {
        AdvancerBindingAnnotation advAnno = AnnotationUtils.findAnnotation(aClass, AdvancerBindingAnnotation.class);
        InstructionBindingAnnotation instAnno = AnnotationUtils.findAnnotation(aClass, InstructionBindingAnnotation.class);
        Validate.notNull(advAnno, "AdvancerBindingAnnotation is null");
        StatusAdvancer statusAdvancer = (StatusAdvancer) autowireCapableBeanFactory.autowire(aClass, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
        AdvancerBinding advancerBinding = new AdvancerBinding();
        advancerBinding.setStatusAdvancer(statusAdvancer);
        Object statusValue = AdvancerBindingAnnotationUtils.parseStatusValue(advAnno);
        if (instAnno != null) {
            Object inst = InstructionBindingAnnotationUtils.parseInstructionValue(instAnno);
            advancerBinding.setKey(Pair.of(statusValue, inst));
        } else {
            advancerBinding.setKey(statusValue);
        }
        return advancerBinding;
    }
}
