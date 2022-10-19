package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface SearchContainer extends Container {
    <T> Collection<T> findAllByInterface(Class<T> _interface);

    <T> Collection<T> findAllBySuperClass(Class<T> superClass);

    Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation);
}
