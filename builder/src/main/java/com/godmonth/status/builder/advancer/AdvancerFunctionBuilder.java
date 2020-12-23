package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.annotations.Advancer;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.Builder;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class AdvancerFunctionBuilder {


    @Builder
    public static Function<Object, StatusAdvancer> build(String packageName, Class modelClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory) throws IOException, ClassNotFoundException {
        Map<Object, StatusAdvancer> map = new HashMap<>();
        ClassPath from = ClassPath.from(ClassLoader.getSystemClassLoader());
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = from.getTopLevelClassesRecursive(packageName);

        for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
            Class<?> aClass = Class.forName(topLevelClass.getName());
            Advancer annotation = AnnotationUtils.findAnnotation(aClass, Advancer.class);
            if (annotation != null && modelClass.equals(annotation.modelClass())) {
                if (predicate != null && !predicate.test(aClass)) {
                    continue;
                }
                StatusAdvancer statusAdvancer = (StatusAdvancer) autowireCapableBeanFactory.autowire(aClass, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
                if (statusAdvancer != null) {
                    map.put(statusAdvancer.getKey(), statusAdvancer);
                }
            }
        }
        return map::get;
    }


}
