package jw.spigot_fluent_api.desing_patterns.dependecy_injection.builder;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;

public interface ContainerBuilder
{
    public ContainerBuilder register(Class _class, LifeTime lifeTime);

    public ContainerBuilder registerSigleton(Class _class);

    public ContainerBuilder registerTransient(Class _class);

    public ContainerBuilder disableAutoRegistration();
}
