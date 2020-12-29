package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.annotations.Advancer;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <p></p >
 *
 * @author shenyue
 */
@Slf4j
public class AdvancerFunctionBuilder {


    @Builder
    private static Function<Object, StatusAdvancer> build(@Singular Set<String> packageNames, Class modelClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory, List<StatusAdvancer> advancers) throws IOException, ClassNotFoundException {
        Map<Object, StatusAdvancer> map = new HashMap<>();
        ClassPath from = ClassPath.from(ClassLoader.getSystemClassLoader());
        if (packageNames != null) {
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
                                map.put(statusAdvancer.getKey(), statusAdvancer);
                            }
                        }
                    }

                }
            }
        }
        if (advancers != null) {
            for (StatusAdvancer statusAdvancer : advancers) {
                map.put(statusAdvancer.getKey(), statusAdvancer);
            }
        }
        log.debug("advanceMap:{}", map);
        return map::get;
    }


}
