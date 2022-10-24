package jw.fluent_plugin.implementation.modules.mediator;

import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_api.desing_patterns.mediator.implementation.SimpleMediator;

public class FluentMediatorImpl implements FluentMediator
{
    private final SimpleMediator simpleMediator;

    public FluentMediatorImpl() {
        simpleMediator = new SimpleMediator();
    }

    public <Output> Output resolve(Object input, Class<Output> outputClass) {
        return simpleMediator.resolve(input, outputClass);
    }

    public <T extends MediatorHandler<?, ?>> void register(T mediator) {
        simpleMediator.register(mediator);
    }
}