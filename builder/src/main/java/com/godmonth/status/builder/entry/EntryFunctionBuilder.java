package com.godmonth.status.builder.entry;

import com.godmonth.status.annotations.StatusEntryBinding;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
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
public class EntryFunctionBuilder {
    @Builder
    private static <STATUS> Function<STATUS, StatusEntry> build(String packageName, Class statusClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory, Map<STATUS, StatusEntry> entryMap) throws IOException, ClassNotFoundException {
        Map<Object, StatusEntry> map = new HashMap<>();
        ClassPath from = ClassPath.from(ClassLoader.getSystemClassLoader());
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = from.getTopLevelClassesRecursive(packageName);

        for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
            Class aClass = Class.forName(topLevelClass.getName());
            StatusEntryBinding annotation = AnnotationUtils.findAnnotation(aClass, StatusEntryBinding.class);
            if (annotation != null && statusClass.equals(annotation.statusClass())) {
                if (predicate != null && !predicate.test(aClass)) {
                    continue;
                }


                StatusEntry statusEntry = (StatusEntry) autowireCapableBeanFactory.autowire(aClass, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
                if (statusEntry != null) {
                    if (aClass.equals(String.class)) {
                        map.put(annotation.statusValue(), statusEntry);
                    } else if (aClass.isEnum()) {
                        Enum anEnum = Enum.valueOf(annotation.statusClass(), annotation.statusValue());
                        map.put(anEnum, statusEntry);
                    }
                }
            }
        }
        if (entryMap != null) {
            map.putAll(entryMap);
        }

        return map::get;
    }
}
