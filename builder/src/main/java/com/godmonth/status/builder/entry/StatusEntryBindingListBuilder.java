package com.godmonth.status.builder.entry;

import com.godmonth.status.annotations.StatusEntryBindingAnnotation;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.StatusEntryBinding;
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
public class StatusEntryBindingListBuilder {
    @Builder
    private static List<StatusEntryBinding> build(@Singular Set<String> packageNames, Class statusClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        Validate.notEmpty(packageNames, "packageNames is empty");
        List<StatusEntryBinding> list = new ArrayList<>();
        ClassPath classPath = ClassPath.from(classLoader != null ? classLoader : ClassLoader.getSystemClassLoader());
        for (String packageName : packageNames) {
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = classPath.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
                Class aClass = Class.forName(topLevelClass.getName());
                if (StatusEntry.class.isAssignableFrom(aClass)) {
                    StatusEntryBindingAnnotation annotation = AnnotationUtils.findAnnotation(aClass, StatusEntryBindingAnnotation.class);
                    if (annotation != null && statusClass.equals(annotation.statusClass())) {
                        if (predicate != null && !predicate.test(aClass)) {
                            continue;
                        }
                        StatusEntry statusEntry = (StatusEntry) autowireCapableBeanFactory.autowire(aClass, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
                        if (statusEntry != null) {
                            StatusEntryBinding.StatusEntryBindingBuilder builder = StatusEntryBinding.builder().statusEntry(statusEntry);
                            if (aClass.equals(String.class)) {
                                builder.previousStatus(annotation.statusValue());
                            } else if (annotation.statusClass().isEnum()) {
                                Enum anEnum = Enum.valueOf(annotation.statusClass(), annotation.statusValue());
                                builder.previousStatus(anEnum);
                            } else {
                                throw new IllegalArgumentException("status entry cfg illegal:" + aClass);
                            }
                            list.add(builder.build());
                        }
                    }
                }
            }
        }
        log.debug("entry list:{}", list);
        return list;
    }
}
