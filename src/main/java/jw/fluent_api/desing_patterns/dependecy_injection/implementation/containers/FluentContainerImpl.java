package jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.EventHandler;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.logger.api.SimpleLogger;
import jw.fluent_api.utilites.java.ObjectUtility;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.util.*;

public class FluentContainerImpl extends DefaultContainer implements FluentContainer {

    public FluentContainerImpl(InstanceProvider instaneProvider,
                               EventHandler eventHandler,
                               SimpleLogger logger,
                               InjectionInfoFactory injectionInfoFactory,
                               List<RegistrationInfo> registrationInfos) {
        super(instaneProvider, eventHandler, logger, injectionInfoFactory, registrationInfos);
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
