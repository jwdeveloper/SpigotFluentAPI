package jw.fluent_api.desing_patterns.dependecy_injection.implementation.search;

import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.search.SearchAgent;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class SearchAgentImpl implements SearchAgent {



    public <T> Collection<T> findAllByInterface(Function<Class<?>,Object> find, Map<Class<?>, InjectionInfo> injections, Class<T> _interface) {
        var result = new ArrayList<T>();
        for (var set : injections.entrySet()) {
            var injection = set.getValue();
            if (!injection.hasInterface(_interface))
                continue;

            result.add( (T)find.apply(set.getKey()));
        }
        return result;
    }

    public <T> Collection<T> findAllBySuperClass(Function<Class<?>,Object> find, Map<Class<?>, InjectionInfo> injections, Class<T> superClass) {
        var result = new ArrayList<T>();
        for (var set : injections.entrySet()) {
            var injection = set.getValue();
            if (!injection.hasSuperClass(superClass))
                continue;

            result.add((T)find.apply(set.getKey()));
        }
        return result;
    }

    public Collection<Object> findAllByAnnotation(Function<Class<?>,Object> find, Map<Class<?>, InjectionInfo> injections, Class<? extends Annotation> _annotation) {
        var result = new ArrayList<>();
        for (var set : injections.entrySet()) {
            var injection = set.getValue();
            if (!injection.hasAnnotation(_annotation))
                continue;

            result.add(find.apply(set.getKey()));
        }
        return result;
    }
}
