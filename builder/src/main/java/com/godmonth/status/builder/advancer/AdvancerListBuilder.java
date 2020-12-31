package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.annotations.Advancer;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
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
public class AdvancerListBuilder {

    @Builder
    private static List<StatusAdvancer> build(@Singular Set<String> packageNames, Class modelClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory) throws IOException, ClassNotFoundException {
        Validate.notEmpty(packageNames, "packageNames is empty");
        List<StatusAdvancer> list = new ArrayList<>();
        ClassPath from = ClassPath.from(ClassLoader.getSystemClassLoader());
        for (String packageName : packageNames) {
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = from.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
                Class<?> aClass = Class.forName(topLevelClass.getName());
                if (StatusAdvancer.class.isAssignableFrom(aClass)) {
                    Advancer annotation = AnnotationUtils.findAnnotation(aClass, Advancer.class);
                    if (annotation != null && modelClass.equals(annotation.value())) {
                        if (predicate != null && !predicate.test(aClass)) {
                            continue;
                        }
                        StatusAdvancer statusAdvancer = (StatusAdvancer) autowireCapableBeanFactory.autowire(aClass, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
                        if (statusAdvancer != null) {
                            list.add(statusAdvancer);
                        }
                    }
                }

            }

        }
        log.debug("advancerList:{}", list);
        return list;
    }


}
