package jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.SearchContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.EventHandler;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.logger.api.SimpleLogger;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class SearchContainerImpl extends DefaultContainer implements SearchContainer {

    public SearchContainerImpl(InstanceProvider instaneProvider,
                               EventHandler eventHandler,
                               SimpleLogger logger,
                               InjectionInfoFactory injectionInfoFactory,
                               Map<Class<?>, InjectionInfo> injections) {
        super(instaneProvider, eventHandler, logger, injectionInfoFactory, injections);
    }

    public <T> Collection<T> findAllByInterface(Class<T> _interface) {
        var result = new ArrayList<T>();
        for (var set : injections.entrySet()) {
            var injection = set.getValue();
            if (!injection.hasInterface(_interface))
                continue;

            var instnace = (T)find(set.getKey());
            result.add(instnace);
        }
        return result;
    }

    public <T> Collection<T> findAllBySuperClass(Class<T> superClass) {
        var result = new ArrayList<T>();
        for (var set : injections.entrySet()) {
            var injection = set.getValue();
            if (!injection.hasSuperClass(superClass))
                continue;

            var instnace = (T)find(set.getKey());
            result.add(instnace);
        }
        return result;
    }

    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation) {
        var result = new ArrayList<>();
        for (var set : injections.entrySet()) {
            var injection = set.getValue();
            if (!injection.hasAnnotation(_annotation))
                continue;

            var instance = find(set.getKey());
            result.add(instance);
        }
        return result;
    }
}
