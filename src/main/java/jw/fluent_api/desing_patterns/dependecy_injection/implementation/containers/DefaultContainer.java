package jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.EventHandler;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.search.ContainerSearch;
import jw.fluent_api.desing_patterns.dependecy_injection.api.search.SearchAgent;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.utilites.Messages;
import jw.fluent_api.logger.api.SimpleLogger;
import jw.fluent_api.utilites.java.ObjectUtility;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DefaultContainer implements Container, ContainerSearch {
    protected final EventHandler eventHandler;
    protected final InstanceProvider instaneProvider;
    protected final SimpleLogger logger;
    protected final Map<Class<?>, InjectionInfo> injections;
    protected final InjectionInfoFactory injectionInfoFactory;
    protected final SearchAgent searchAgent;

    public DefaultContainer(SearchAgent searchAgent,
                            InstanceProvider instaneProvider,
                            EventHandler eventHandler,
                            SimpleLogger logger,
                            InjectionInfoFactory injectionInfoFactory,
                            List<RegistrationInfo> registrationInfos) {
        this.instaneProvider = instaneProvider;
        this.eventHandler = eventHandler;
        this.logger = logger;
        this.injectionInfoFactory = injectionInfoFactory;
        this.searchAgent = searchAgent;
        this.injections = new HashMap<>();

        for (var registration : registrationInfos) {
            register(registration);
        }
    }

    @Override
    public boolean register(RegistrationInfo registrationInfo) {
        Class<?> clazz = switch (registrationInfo.registrationType()) {
            case InterfaceAndIml, InterfaceAndProvider, List -> registrationInfo._interface();
            case OnlyImpl -> registrationInfo.implementation();
        };
        if (injections.containsKey(clazz)) {
            logger.error(String.format(Messages.INJECTION_ALREADY_EXISTS, clazz.getSimpleName()));
            return false;
        }
        try {
            var pair = injectionInfoFactory.create(registrationInfo);
            var regisrationResult = eventHandler.OnRegistration(new OnRegistrationEvent(pair.key(), pair.value(), registrationInfo));
            if (!regisrationResult) {
                return false;
            }
            injections.put(pair.key(), pair.value());
        } catch (Exception e) {
            logger.error(String.format(Messages.INJECTION_CANT_REGISTER, clazz.getSimpleName()));
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Override
    public Object find(Class<?> _injection) {
        var injectionInfo = injections.get(_injection);
        if (injectionInfo == null) {
            logger.error(String.format(Messages.INJECTION_NOT_FOUND, _injection.getSimpleName()));
            return eventHandler.OnInjection(new OnInjectionEvent(_injection, injectionInfo, null, injections, this));
        }
        try {
            var result = instaneProvider.getInstance(injectionInfo, injections, this);
            return eventHandler.OnInjection(new OnInjectionEvent(_injection, injectionInfo, result, injections, this));
        } catch (Exception e) {
            logger.error(String.format(Messages.INJECTION_CANT_CREATE, _injection.getSimpleName()), e);
            return null;
        }
    }


    @Override
    public <T> Collection<T> findAllByInterface(Class<T> _interface) {
        return searchAgent.<T>findAllByInterface(this::find,injections,_interface);
    }

    @Override
    public <T> Collection<T> findAllBySuperClass(Class<T> superClass) {
        return searchAgent.<T>findAllBySuperClass(this::find,injections,superClass);
    }

    @Override
    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation) {
        return searchAgent.findAllByAnnotation(this::find,injections,_annotation);
    }
}
