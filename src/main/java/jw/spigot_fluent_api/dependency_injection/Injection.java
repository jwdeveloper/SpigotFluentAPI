package jw.spigot_fluent_api.dependency_injection;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Injection {
    private final InjectionType serviceType;
    private final Class<?> aClass;
    private Object instance;
    private Constructor<?> constructor;
    private Object[] params;
    private Class<?>[] parametersType;

    public Injection(InjectionType serviceType, Class<?> aClass) {
        this.aClass = aClass;
        this.serviceType = serviceType;
        if (aClass.getConstructors().length > 0) {
            this.constructor = aClass.getConstructors()[0];
            if (this.constructor.getParameterCount() > 0)
                this.params = new Object[constructor.getParameterCount()];
            this.parametersType = constructor.getParameterTypes();
        }
    }

    public Class<?> getType() {
        return aClass;
    }

    public boolean setParams(InjectionMapper mapParams) {
        if (constructor == null || params == null) {
            return true;
        }

        for (int i = 0; i < parametersType.length; i++) {
            params[i] = mapParams.map(parametersType[i]);

            if (params[i] == null) {
                FluentPlugin.logError("Constructor parameter with type " +
                        parametersType[i] +
                        " at index " + i +
                        " Not found in " +
                        getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }

    public boolean isInit() {
        return instance != null;
    }

    public InjectionType getInjectionType() {
        return serviceType;
    }

    public <T> T getInstance() {
        return (T) instance;
    }

    public void createInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (constructor != null) {
            if (params != null)
                this.instance = this.constructor.newInstance(params);
            else
                this.instance = this.constructor.newInstance();
        }

    }

    @Override
    public String toString() {
        return  "Injection " + this.hashCode() +
                "Injection type " + serviceType +
                "Is init " + this.isInit();
    }
}
