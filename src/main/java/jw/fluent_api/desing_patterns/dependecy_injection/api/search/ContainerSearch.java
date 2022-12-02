package jw.fluent_api.desing_patterns.dependecy_injection.api.search;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

public interface ContainerSearch
{
    public <T> Collection<T> findAllByInterface(Class<T> _interface);

    public <T> Collection<T> findAllBySuperClass(Class<T> superClass);

    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation);
}
