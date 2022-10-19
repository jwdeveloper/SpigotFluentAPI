package jw.fluent_api.desing_patterns.decorator.implementation;

import jw.fluent_api.desing_patterns.decorator.api.Decorator;
import jw.fluent_api.desing_patterns.decorator.api.DecoratorInstanceProvider;
import jw.fluent_api.desing_patterns.decorator.api.models.DecorationDto;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;

import java.util.Map;

public class DefaultDecorator implements Decorator
{
    private final Map<Class<?>, DecorationDto> decorators;
    private final DecoratorInstanceProvider decoratorInstanceProvider;

    public DefaultDecorator(DecoratorInstanceProvider decoratorInstanceProvider,
                            Map<Class<?>, DecorationDto> decorators) {
        this.decorators = decorators;
        this.decoratorInstanceProvider = decoratorInstanceProvider;
    }

    @Override
    public boolean OnRegistration(OnRegistrationEvent event) {
        return true;
    }

    public Object OnInjection(OnInjectionEvent event) throws Exception {
        var decoratorDto =  decorators.get(event.input());
        if(decoratorDto == null)
        {
            return event.result();
        }
        var result = event.result();
        for(var injectionInfo : decoratorDto.implementations())
        {

            var nextDecorator = decoratorInstanceProvider.getInstance(
                    injectionInfo,
                    event.injectionInfoMap(),
                    result);
            result = nextDecorator;
        }
        return result;
    }
}
