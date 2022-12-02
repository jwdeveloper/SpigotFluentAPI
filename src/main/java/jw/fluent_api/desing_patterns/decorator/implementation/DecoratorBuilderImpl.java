package jw.fluent_api.desing_patterns.decorator.implementation;

import jw.fluent_api.desing_patterns.decorator.api.Decorator;
import jw.fluent_api.desing_patterns.decorator.api.builder.DecoratorBuilder;
import jw.fluent_api.desing_patterns.decorator.api.models.DecorationDto;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecoratorBuilderImpl implements DecoratorBuilder {

    private Map<Class<?>, List<Class<?>>> registeredTypes;
    private InjectionInfoFactory injectionInfoFactory;

    public DecoratorBuilderImpl(InjectionInfoFactory injectionInfoFactory,  Map<Class<?>, List<Class<?>>> decorators)
    {
        this.injectionInfoFactory = injectionInfoFactory;
        this.registeredTypes = decorators;
    }

    @Override
    public <T> DecoratorBuilder decorate(Class<T> _interface, Class<? extends T> implementation)
    {
        if(!registeredTypes.containsKey(_interface))
        {
            registeredTypes.put(_interface,new ArrayList<>());
        }
        var implementations = registeredTypes.get(_interface);
        implementations.add(implementation);
        return this;
    }

    @Override
    public Decorator build() throws Exception {

        var instanceProvider = new DecoratorInstanceProviderImpl();
        var decorators = createDecoratorDto();
        return new DefaultDecorator(instanceProvider, decorators);
    }

    private Map<Class<?>, DecorationDto> createDecoratorDto() throws Exception {
        var decorators = new HashMap<Class<?>, DecorationDto>();
        for(var pair : registeredTypes.entrySet())
        {
            var injectionsInfoList = new ArrayList<InjectionInfo>();
            for(var implementation : pair.getValue())
            {
                var registrationInfo = new RegistrationInfo(
                        pair.getKey(),
                        implementation,
                        null,
                        LifeTime.SINGLETON,
                        RegistrationType.InterfaceAndIml);
                var pari = injectionInfoFactory.create(registrationInfo);
                injectionsInfoList.add(pari.value());
            }
            var decorationDto = new DecorationDto(pair.getKey(), injectionsInfoList);
            decorators.put(pair.getKey(), decorationDto);
        }
        return decorators;
    }
}
