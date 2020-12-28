package com.godmonth.status.builder.entry;

import com.godmonth.status.annotations.StatusEntryBinding;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.IOException;
import java.util.HashMap;
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
public class EntryFunctionBuilder {
    @Builder
    private static <STATUS> Function<STATUS, StatusEntry> build(@Singular Set<String> packageNames, Class statusClass, Predicate<Class<?>> predicate, AutowireCapableBeanFactory autowireCapableBeanFactory, Map<STATUS, StatusEntry> entryMap) throws IOException, ClassNotFoundException {
        Map<Object, StatusEntry> map = new HashMap<>();
        ClassPath from = ClassPath.from(ClassLoader.getSystemClassLoader());
        if (packageNames != null) {
            for (String packageName : packageNames) {
                ImmutableSet<ClassPath.ClassInfo> topLevelClasses = from.getTopLevelClassesRecursive(packageName);
                for (ClassPath.ClassInfo topLevelClass : topLevelClasses) {
                    Class aClass = Class.forName(topLevelClass.getName());
                    if (StatusEntry.class.isAssignableFrom(aClass)) {
                        StatusEntryBinding annotation = AnnotationUtils.findAnnotation(aClass, StatusEntryBinding.class);
                        if (annotation != null && statusClass.equals(annotation.statusClass())) {
                            if (predicate != null && !predicate.test(aClass)) {
                                continue;
                            }
                            StatusEntry statusEntry = (StatusEntry) autowireCapableBeanFactory.autowire(aClass, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
                            if (statusEntry != null) {
                                if (aClass.equals(String.class)) {
                                    map.put(annotation.statusValue(), statusEntry);
                                } else if (annotation.statusClass().isEnum()) {
                                    Enum anEnum = Enum.valueOf(annotation.statusClass(), annotation.statusValue());
                                    map.put(anEnum, statusEntry);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (entryMap != null) {
            map.putAll(entryMap);
        }
        log.debug("entryMap:{}", map);
        return map::get;
    }
}
