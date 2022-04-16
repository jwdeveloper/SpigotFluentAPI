package jw.spigot_fluent_api.desing_patterns.dependecy_injection;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.factory.InjectedClassFactory;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.utilites.Messages;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Container {
    private HashMap<Class<?>, InjectedClass> injections;

    public Container() {
        injections = new HashMap<>();
    }

    public <T> T get(Class<T> _injection) {
        if (!injections.containsKey(_injection)) {
            FluentPlugin.logError(String.format(Messages.INJECTION_NOT_FOUND, _injection.getSimpleName()));
            return null;
        }
        try {
            return (T) injections.get(_injection).getInstnace(injections);
        } catch (Exception e) {
            FluentLogger.error(String.format(Messages.INJECTION_CANT_CREATE, _injection.getSimpleName()), e);
        }
        return null;
    }

    public <T> Collection<T> getAllByInterface(Class<T> _interface) {
        var result = new ArrayList<T>();
        for (var injection : injections.values()) {
            if (!injection.hasInterface(_interface))
                continue;

            result.add(get((Class<T>) injection.getType()));
        }
        return result;
    }

    public <T> Collection<T> getAllByParentType(Class<T> _Parent) {
        var result = new ArrayList<T>();
        for (var injection : injections.values()) {
            if (!injection.hasParentType(_Parent))
                continue;

            result.add(get((Class<T>) injection.getType()));
        }
        return result;
    }

    public Collection<Object> getAllByAnnotation(Class<? extends Annotation> _annotation) {
        var result = new ArrayList<>();
        for (var injection : injections.values()) {
            if (!injection.hasAnnotation(_annotation))
                continue;
            result.add(get(injection.getType()));
        }
        return result;
    }

    public boolean register(Class<?> _class, LifeTime lifeTime) {

        if (injections.containsKey(_class)) {
            FluentPlugin.logError(String.format(Messages.INJECTION_CANT_CREATE, _class.getSimpleName()));
            return false;
        }
        try {
            var injection = InjectedClassFactory.getFromClass(_class, lifeTime);
            injections.put(_class, injection);
            return true;
        } catch (Exception e) {
            FluentLogger.error("Error while register class", e);
            return false;
        }
    }

    public void register(HashMap<Class<?>, LifeTime> _classes) {
        _classes.forEach((this::register));
    }

}
