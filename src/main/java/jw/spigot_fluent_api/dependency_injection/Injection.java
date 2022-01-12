package jw.spigot_fluent_api.dependency_injection;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Injection {
    private final InjectionType serviceType;
    private final Class<?> type;
    private Object instance;
    private Constructor<?> constructor;
    private Object[] constructorParams;
    private Class<?>[] constructorParamsType;

    public Injection(InjectionType injectionType, Class<?> type) {
        this.type = type;
        this.serviceType = injectionType;
        if (type.getConstructors().length > 0) {
            this.constructor = type.getConstructors()[0];
            if (this.constructor.getParameterCount() > 0)
                this.constructorParams = new Object[constructor.getParameterCount()];
            this.constructorParamsType = constructor.getParameterTypes();
        }
    }

    public Class<?> getType() {
        return type;
    }

    public boolean setParams(InjectionMapper mapParams) {
        if (constructor == null || constructorParams == null) {
            return true;
        }

        for (int i = 0; i < constructorParamsType.length; i++) {
            constructorParams[i] = mapParams.map(constructorParamsType[i]);

            if (constructorParams[i] == null) {
                FluentPlugin.logError("Constructor parameter with type " +
                        constructorParamsType[i] +
                        " at index " + i +
                        " Not found in " +
                        getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }

    public boolean hasInstance() {
        return instance != null;
    }

    public InjectionType getInjectionType() {
        return serviceType;
    }

    public <T> T getInstance() {
        return (T) instance;
    }

    public void createInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (constructor == null)
            return;


        if (constructorParams != null)
            this.instance = this.constructor.newInstance(constructorParams);
        else
            this.instance = this.constructor.newInstance();
    }

    @Override
    public String toString() {
        return "Injection " + this.hashCode() +
                "Injection type " + serviceType +
                "has Instance " + this.hasInstance();
    }
}
