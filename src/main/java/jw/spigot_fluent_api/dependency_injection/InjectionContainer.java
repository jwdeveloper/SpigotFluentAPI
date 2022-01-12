package jw.spigot_fluent_api.dependency_injection;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.HashMap;

public class InjectionContainer {
    private final HashMap<Class<?>, Injection> container = new HashMap<>();

    public HashMap<Class<?>, Injection> getInjections() {
        return container;
    }

    public Injection getInjection(Class<?> type) {
        if (!container.containsKey(type)) {
            return null;
        }
        return container.get(type);
    }

    public <T> void register(InjectionType injectionType, Class<T> type) {
        container.putIfAbsent(type, new Injection(injectionType, type));
    }

    public <T> T getObject(Class<?> type) {
        try {
            if (!container.containsKey(type)) {
                FluentPlugin.logError("Injection for class " + type.getTypeName() + " has not been found!");
                return null;
            }
            Injection injection = container.get(type);
            if (injection.getInjectionType() == InjectionType.TRANSIENT ||
                    !injection.hasInstance()) {
                //for each constructor param method getObject(paramClass) is invoked
                if (injection.setParams(this::getObject)) {
                    injection.createInstance();
                } else {
                    FluentPlugin.logError("Count not create " + type.getTypeName() + " due to null parameter in constructor");
                    return null;
                }
            }
            return injection.getInstance();
        } catch (Exception e) {
            FluentPlugin.logException("Can not get injection for class " + type.getTypeName(), e);
            return null;
        }
    }

    public void dispose() {
        container.clear();
    }

}
