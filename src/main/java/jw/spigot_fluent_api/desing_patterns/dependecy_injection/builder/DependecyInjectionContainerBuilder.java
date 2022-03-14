package jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;

import java.util.HashSet;
import java.util.Set;

public class DependecyInjectionContainerBuilder implements ContainerBuilder
{
    private final Container container;
    private final Set<Class<?>> registered;
    private boolean autoRegistration = true;

    public DependecyInjectionContainerBuilder()
    {
        container = new Container();
        registered = new HashSet<>();
    }

    public ContainerBuilder register(Class _class, LifeTime lifeTime)
    {
        container.register(_class,lifeTime);
        registered.add(_class);
        return this;
    }

    public ContainerBuilder registerSigleton(Class _class)
    {
        container.register(_class,LifeTime.SINGLETON);
        registered.add(_class);
        return this;
    }

    public ContainerBuilder registerTransient(Class _class)
    {
        container.register(_class,LifeTime.TRANSIENT);
        registered.add(_class);
        return this;
    }

    @Override
    public ContainerBuilder disableAutoRegistration() {
        autoRegistration = false;
        return this;
    }

    public boolean isAutoRegistration() {
        return autoRegistration;
    }

    public Set<Class<?>> getRegistered()
    {
        return registered;
    }

    public Container build()
    {
        return container;
    }
}
