package jw.fluent_api.desing_patterns.dependecy_injection.api.containers.builders;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.ContainerConfiguration;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiContainerBuilder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ContainerBuilder<Builder extends ContainerBuilder<Builder>> {
    Builder register(Class<?> _class, LifeTime lifeTime);

    <T> Builder register(Class<T> _interface, Class<? extends T> implementation, LifeTime lifeTime);

    <T> Builder register(Class<T> _interface, LifeTime lifeTime, Function<Container, Object> provider);

    <T> Builder registerList(Class<T> _interface, LifeTime lifeTime);

    <T> Builder registerList(Class<T> _interface, LifeTime lifeTime, Function<Container, Object> provider);

    Builder registerSingletonList(Class<?> _interface);

    Builder registerTransientList(Class<?> _interface);

    Builder registerSigleton(Class<?> _class);

    Builder registerTransient(Class<?> _class);

    Builder configure(Consumer<ContainerConfiguration> configuration);

    <T> Builder registerSigleton(Class<T> _interface, Class<? extends T> implementation);

    <T> Builder registerTransient(Class<T> _interface, Class<? extends T> implementation);

    Builder registerSigleton(Class<?> _interface, Object instance);

    Builder registerTrasient(Class<?> _interface, Object instance);
}
