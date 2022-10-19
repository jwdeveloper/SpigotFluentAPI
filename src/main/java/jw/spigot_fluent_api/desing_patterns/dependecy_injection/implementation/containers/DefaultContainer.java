package jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.EventHandler;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnInjectionEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnRegistrationEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.utilites.Messages;
import jw.spigot_fluent_api.fluent_logger.api.SimpleLogger;
import jw.spigot_fluent_api.utilites.java.ObjectUtility;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class DefaultContainer implements Container {
    private final EventHandler eventHandler;
    private final InstanceProvider instaneProvider;
    private final SimpleLogger logger;

    protected final Map<Class<?>, InjectionInfo> injections;
    private final InjectionInfoFactory injectionInfoFactory;

    public DefaultContainer(InstanceProvider instaneProvider,
                            EventHandler eventHandler,
                            SimpleLogger logger,
                            InjectionInfoFactory injectionInfoFactory,
                            Map<Class<?>, InjectionInfo> injections) {
        this.injections = injections;
        this.instaneProvider = instaneProvider;
        this.eventHandler = eventHandler;
        this.logger = logger;
        this.injectionInfoFactory = injectionInfoFactory;
    }

    @Override
    public boolean register(RegistrationInfo registrationInfo)  {
        Class<?> clazz = switch (registrationInfo.registrationType()) {
            case InterfaceAndIml, InterfaceAndProvider -> registrationInfo._interface();
            case OnlyImpl -> registrationInfo.implementation();
        };
        if(injections.containsKey(clazz))
        {
            logger.error(String.format(Messages.INJECTION_ALREADY_EXISTS, clazz.getSimpleName()));
            return false;
        }
        try
        {
            var pair = injectionInfoFactory.create(registrationInfo);
            var regisrationResult = eventHandler.OnRegistration(new OnRegistrationEvent(pair.key(),pair.value(),registrationInfo));
            if(!regisrationResult)
            {
                logger.info(String.format(Messages.INJECTION_CANT_REGISTER, clazz.getSimpleName()));
                return false;
            }
            injections.put(pair.key(),pair.value());
        }
        catch (Exception e)
        {
            logger.error(String.format(Messages.INJECTION_CANT_REGISTER, clazz.getSimpleName()));
            return false;
        }
        return true;
    }

    @Override
    public Object find(Class<?> _injection) {
        var injectionInfo = injections.get(_injection);
        if (injectionInfo == null) {
            logger.error(String.format(Messages.INJECTION_NOT_FOUND, _injection.getSimpleName()));
            return null;
        }
        try {
            var result = instaneProvider.getInstance(injectionInfo, injections);
            return eventHandler.OnInjection(new OnInjectionEvent(_injection, injectionInfo, result));
        } catch (Exception e) {
            logger.error(String.format(Messages.INJECTION_CANT_CREATE, _injection.getSimpleName()), e);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public Container clone()
    {
       var clonedInjections = new HashMap<Class<?>, InjectionInfo>();
       for(var injection : injections.entrySet())
       {
           var value = (InjectionInfo)ObjectUtility.copyObject(injection.getValue(),InjectionInfo.class);
           value.setInstnace(null);
           clonedInjections.put(injection.getKey(), value);
       }
       return new DefaultContainer(instaneProvider,
               eventHandler,
               logger,
               injectionInfoFactory,
               clonedInjections);
    }


}
