package jw.spigot_fluent_api.desing_patterns.decorator;

import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.event_handler.events.OnInjectionEvent;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.implementation.provider.InstanceProviderImpl;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.*;

public class FluentDecorator
{
    private static FluentDecorator INSTANCE;

    private Map<Class<?>,DecoratorDto> decorators = new HashMap<>();
    private InstanceProvider instanceProvider;

    public FluentDecorator()
    {
        instanceProvider = new InstanceProviderImpl();
        instanceProvider.
    }

    public Object OnInjection(OnInjectionEvent event)
    {
        if(!decorators.containsKey(event.input()))
        {
            return event.result();
        }

        event.


    }

    @Data
    public class DecoratorDto
    {
       private Class<?> _interface;
       private List<InjectionInfo> implementations;
    }
}
