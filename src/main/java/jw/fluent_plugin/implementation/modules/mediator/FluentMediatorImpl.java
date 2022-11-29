package jw.fluent_plugin.implementation.modules.mediator;

import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_api.desing_patterns.mediator.implementation.SimpleMediator;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class FluentMediatorImpl implements FluentMediator
{
    private  SimpleMediator simpleMediator;

    public FluentMediatorImpl(Function<Class<?>,Object> serviceResolver)
    {
        simpleMediator = new SimpleMediator(serviceResolver);
    }

    public FluentMediatorImpl(Collection<Class<?>> mediators, Function<Class<?>,Object> serviceResolver)
    {
        this(serviceResolver);
        for(var mediator : mediators)
        {
            register((Class<MediatorHandler<?, ?>>)mediator);
        }
    }

    public <Output> Output resolve(Object input, Class<Output> outputClass) {
        return simpleMediator.resolve(input, outputClass);
    }

    public <T extends MediatorHandler<?, ?>> void register(Class<T> mediator) {
        simpleMediator.register(mediator);
    }
}
