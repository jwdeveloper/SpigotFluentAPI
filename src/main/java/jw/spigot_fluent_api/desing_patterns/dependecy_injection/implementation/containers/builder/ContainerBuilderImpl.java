package jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.PlayersContainer;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.SearchContainer;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilder;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.builders.ContainerBuilderConfiguration;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.ContainerConfiguration;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.DefaultContainer;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.SearchContainerImpl;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.SpigotPlayersContainer;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.provider.InstanceProviderImpl;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.event_handler.EventHandlerImpl;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.factory.InjectionInfoFactoryImpl;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class ContainerBuilderImpl implements ContainerBuilder, ContainerBuilderConfiguration {
    private final ContainerConfiguration config;

    public ContainerBuilderImpl() {
        config = new ContainerConfiguration();
    }

    public ContainerConfiguration getConfiguration() {
        return config;
    }

    @SneakyThrows
    public ContainerBuilder register(Class<?> implementation, LifeTime lifeTime) {
        config.addRegistration(new RegistrationInfo(
                null,
                implementation,
                null,
                lifeTime,
                RegistrationType.OnlyImpl
        ));
        addRegisterdType(implementation);
        return this;
    }

    @SneakyThrows
    public <T> ContainerBuilder register(Class<T> _interface, Class<? extends T> implementation, LifeTime lifeTime) {
        config.addRegistration(new RegistrationInfo(
                _interface,
                implementation,
                null,
                lifeTime,
                RegistrationType.InterfaceAndIml
        ));
        addRegisterdType(_interface);
        return this;
    }

    @SneakyThrows
    @Override
    public <T> ContainerBuilder register(Class<T> _interface, LifeTime lifeTime, Function<Object, Object> provider) {
        config.addRegistration(new RegistrationInfo(
                _interface,
                null,
                provider,
                lifeTime,
                RegistrationType.InterfaceAndProvider
        ));
        addRegisterdType(_interface);
        return this;
    }

    private void addRegisterdType(Class<?> type) throws Exception {
        if(config.getRegisterdTypes().contains(type))
        {
            throw new Exception("Type "+type.getSimpleName()+" has been already registerd to container");
        }
        config.getRegisterdTypes().add(type);
    }

    public <T> ContainerBuilder registerSigleton(Class<T> _interface, Class<? extends T> implementation) {
        return register(_interface, implementation, LifeTime.SINGLETON);
    }

    public <T> ContainerBuilder registerTransient(Class<T> _interface, Class<? extends T> implementation) {
        return register(_interface, implementation, LifeTime.TRANSIENT);
    }

    public ContainerBuilder registerSigleton(Class<?> implementation) {
        return register(implementation, LifeTime.SINGLETON);
    }

    public ContainerBuilder registerTransient(Class<?> implementation) {
        return register(implementation, LifeTime.TRANSIENT);
    }


    public ContainerBuilder registerSigleton(Class<?> _interface, Object instance) {
        return register(_interface, LifeTime.SINGLETON, (x) ->
        {
            return instance;
        });
    }

    public ContainerBuilder registerTrasient(Class<?> _interface, Object instance) {
        return register(_interface, LifeTime.TRANSIENT, (x) ->
        {
            return instance;
        });
    }

    @Override
    public ContainerBuilder configure(Consumer<ContainerConfiguration> configuration) {
        configuration.accept(config);
        return this;
    }

    public Container buildDefault() throws Exception {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new InstanceProviderImpl();
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var logger = FluentLogger.CreateLogger();

        var injections = new HashMap<Class<?>, InjectionInfo>();
        for (var registration : config.getRegistrations()) {
            var pair = injectionInfoFactory.create(registration);
            injections.put(pair.key(), pair.value());
        }
        return new DefaultContainer(
                instanceProvider,
                eventHandler,
                logger,
                injectionInfoFactory,
                injections);
    }

    public SearchContainer buildSearch() throws Exception {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new InstanceProviderImpl();
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var logger = FluentLogger.CreateLogger();

        var injections = new HashMap<Class<?>, InjectionInfo>();
        for (var registration : config.getRegistrations()) {
            var pair = injectionInfoFactory.create(registration);
            injections.put(pair.key(), pair.value());
        }
        return new SearchContainerImpl(
                instanceProvider,
                eventHandler,
                logger,
                injectionInfoFactory,
                injections);
    }

    public PlayersContainer buildPlayer() throws Exception {
        var container = buildDefault();
        return new SpigotPlayersContainer(container, new ConcurrentHashMap<>());
    }
}